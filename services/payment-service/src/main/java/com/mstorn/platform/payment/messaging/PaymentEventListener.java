package com.mstorn.platform.payment.messaging;

import com.mstorn.platform.events.KafkaTopics;
import com.mstorn.platform.events.OrderCreatedEvent;
import com.mstorn.platform.events.PaymentCompletedEvent;
import com.mstorn.platform.events.PaymentFailedEvent;
import com.mstorn.platform.payment.application.PaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventListener {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentEventListener(KafkaTemplate<String, Object> kafkaTemplate, PaymentService paymentService) {
        this.kafkaTemplate = kafkaTemplate;
        this.paymentService = paymentService;
    }

    private final PaymentService paymentService;

    @KafkaListener(
            topics = KafkaTopics.INVENTORY_RESERVED,
            groupId = "payment-group"
    )
    public void handle(com.mstorn.platform.events.InventoryReservedEvent event) {

        paymentService.processPayment(event);
    }
}
