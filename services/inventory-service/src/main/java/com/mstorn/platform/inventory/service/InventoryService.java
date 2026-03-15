package com.mstorn.platform.inventory.service;

import com.mstorn.platform.events.*;
import com.mstorn.platform.inventory.messaging.InventoryEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InventoryService {

    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final InventoryEventPublisher inventoryEventPublisher;

    public InventoryService(KafkaTemplate<String, Object> kafkaTemplate, InventoryEventPublisher inventoryEventPublisher) {
        this.kafkaTemplate = kafkaTemplate;
        this.inventoryEventPublisher = inventoryEventPublisher;
    }

    @KafkaListener(
            topics = KafkaTopics.PAYMENT_FAILED,
            groupId = "inventory-group"
    )
    public void handle(PaymentFailedEvent event) {

        releaseInventory(event.getOrderId());

        kafkaTemplate.send(
                KafkaTopics.INVENTORY_RELEASED,
                new InventoryReleasedEvent(event.getOrderId())
        );

    }



    public void reserveStock(OrderCreatedEvent event) {

        log.info("Reserving stock for order {} (qty={})",
                event.getOrderId(), event.getQuantity());

        boolean available = checkInventory(event);

        if (available) {

            InventoryReservedEvent inventoryReservedEvent = new InventoryReservedEvent(event.getOrderId());
            inventoryEventPublisher.publishInventoryReserved(inventoryReservedEvent);

        } else {

            InventoryFailedEvent inventoryFailedEvent = new InventoryFailedEvent(event.getOrderId(), "Out of stock");
            inventoryEventPublisher.publishInventoryFailed(inventoryFailedEvent);

        }
    }

    public boolean checkInventory(OrderCreatedEvent event) {

        // a simple simulation for showcase
        // could later query a database or inventory system

        return Math.random() > 0.2;
    }

    public void releaseInventory(UUID orderId) {

        System.out.println("Releasing inventory for order " + orderId);

    }


}