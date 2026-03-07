package com.mstorn.platform.order.infrastructure.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    private static final Logger log = LoggerFactory.getLogger(OrderEventListener.class);

    @KafkaListener(topics = "order.created", groupId = "order-service")
    public void handleOrderCreated(String message) {
        log.info("Received Kafka event: {}", message);
    }
}