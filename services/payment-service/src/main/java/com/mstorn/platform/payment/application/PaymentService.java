package com.mstorn.platform.payment.application;

import com.mstorn.platform.events.*;
import com.mstorn.platform.payment.infrastructure.messaging.ProcessedEventStore;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ProcessedEventStore processedEventStore;

    public PaymentService(KafkaTemplate<String, Object> kafkaTemplate, ProcessedEventStore processedEventStore) {
        this.kafkaTemplate = kafkaTemplate;
        this.processedEventStore = processedEventStore;
    }

    public void processPayment(InventoryReservedEvent event) {

        if(processedEventStore.alreadyProcessed(event.getEventId())) {
            return;
        }
        processedEventStore.markProcessed(event.getEventId());

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