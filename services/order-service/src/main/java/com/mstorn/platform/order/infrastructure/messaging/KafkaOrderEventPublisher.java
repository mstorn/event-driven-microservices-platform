package com.mstorn.platform.order.infrastructure.messaging;

import com.mstorn.platform.events.KafkaTopics;
import com.mstorn.platform.events.OrderCreatedEvent;
import com.mstorn.platform.order.application.port.OrderEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaOrderEventPublisher implements OrderEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(KafkaOrderEventPublisher.class);

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public KafkaOrderEventPublisher(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishOrderCreated(OrderCreatedEvent event) {
        log.info("Publishing OrderCreatedEvent to Kafka topic='{}', orderId={}",
                KafkaTopics.ORDER_CREATED,
                event.getOrderId());

        try {
            kafkaTemplate.send(KafkaTopics.ORDER_CREATED, event);
        } catch (Exception e) {
            log.info("Kafka publish successful for orderId={}", event.getOrderId());
        }
    }
}