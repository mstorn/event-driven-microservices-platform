package com.mstorn.platform.payment.application;

import com.mstorn.platform.events.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void processPayment(InventoryReservedEvent event) {

        boolean paymentSuccessful = simulatePayment();

        if (paymentSuccessful) {

            kafkaTemplate.send(
                    KafkaTopics.PAYMENT_COMPLETED,
                    new PaymentCompletedEvent(event.getOrderId())
            );

        } else {

            kafkaTemplate.send(
                    KafkaTopics.PAYMENT_FAILED,
                    new PaymentFailedEvent(
                            event.getOrderId(),
                            "Payment declined"
                    )
            );

        }
    }

    private boolean simulatePayment() {

        return Math.random() > 0.2;

    }
}