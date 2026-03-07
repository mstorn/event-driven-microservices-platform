package com.mstorn.platform.inventory.messaging;

import com.mstorn.platform.inventory.event.OrderCreatedEvent;
import com.mstorn.platform.inventory.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    private static final Logger log = LoggerFactory.getLogger(OrderEventListener.class);

    private final InventoryService inventoryService;

    public OrderEventListener(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(
            topics = "order.created",
            groupId = "inventory-group"
    )
    public void handle(OrderCreatedEvent event) {
        log.info("Inventory received order event: {}", event);
        inventoryService.reserveStock(event);
    }
}