package com.mstorn.platform.inventory.messaging;

import com.mstorn.platform.events.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventPublisher {

    private static final Logger log =
            LoggerFactory.getLogger(InventoryEventPublisher.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public InventoryEventPublisher(
            KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishInventoryReserved(InventoryReservedEvent event) {

        log.info("Publishing InventoryReservedEvent orderId={}",
                event.getOrderId());

        kafkaTemplate.send(KafkaTopics.INVENTORY_RESERVED, event);
    }

    public void publishInventoryFailed(InventoryFailedEvent event) {

        log.info("Publishing InventoryFailedEvent orderId={}",
                event.getOrderId());

        kafkaTemplate.send(KafkaTopics.INVENTORY_FAILED, event);
    }

}