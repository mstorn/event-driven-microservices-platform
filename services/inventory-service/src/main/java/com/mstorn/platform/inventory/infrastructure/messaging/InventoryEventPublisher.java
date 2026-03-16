package com.mstorn.platform.inventory.infrastructure.messaging;

import com.mstorn.platform.events.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventPublisher {

    private static final Logger log =
            LoggerFactory.getLogger(InventoryEventPublisher.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ProcessedEventStore processedEventStore;

    public InventoryEventPublisher(
            KafkaTemplate<String, Object> kafkaTemplate, ProcessedEventStore processedEventStore) {
        this.kafkaTemplate = kafkaTemplate;
        this.processedEventStore = processedEventStore;
    }

    public void publishInventoryReserved(InventoryReservedEvent event) {

        if(processedEventStore.alreadyProcessed(event.getEventId())) {
            return;
        }
        processedEventStore.markProcessed(event.getEventId());

        log.info("Publishing InventoryReservedEvent orderId={}",
                event.getOrderId());

        kafkaTemplate.send(KafkaTopics.INVENTORY_RESERVED, event);
    }

    public void publishInventoryFailed(InventoryFailedEvent event) {
        if(processedEventStore.alreadyProcessed(event.getEventId())) {
            return;
        }
        processedEventStore.markProcessed(event.getEventId());

        log.info("Publishing InventoryFailedEvent orderId={}",
                event.getOrderId());

        kafkaTemplate.send(KafkaTopics.INVENTORY_FAILED, event);
    }

}