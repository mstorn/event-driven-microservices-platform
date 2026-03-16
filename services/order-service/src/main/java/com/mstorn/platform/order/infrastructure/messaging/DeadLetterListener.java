package com.mstorn.platform.order.infrastructure.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DeadLetterListener {

    private static final Logger log =
            LoggerFactory.getLogger(DeadLetterListener.class);

    @KafkaListener(topics = "inventory.reserved.DLT")
    public void handleDeadLetter(Object message) {

        log.error("Dead letter event received: {}", message);

    }
}