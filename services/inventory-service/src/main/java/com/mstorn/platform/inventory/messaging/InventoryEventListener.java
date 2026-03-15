package com.mstorn.platform.inventory.messaging;

import com.mstorn.platform.events.InventoryReleasedEvent;
import com.mstorn.platform.events.KafkaTopics;
import com.mstorn.platform.events.OrderCreatedEvent;
import com.mstorn.platform.events.PaymentFailedEvent;
import com.mstorn.platform.inventory.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventListener {

    private static final Logger log = LoggerFactory.getLogger(InventoryEventListener.class);

    private final InventoryService inventoryService;

    public InventoryEventListener(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(
            topics = KafkaTopics.ORDER_CREATED,
            groupId = "inventory-group"
    )
    public void handle(OrderCreatedEvent event) {
        log.info("Inventory received order event: {}", event);
        inventoryService.reserveStock(event);
    }
}