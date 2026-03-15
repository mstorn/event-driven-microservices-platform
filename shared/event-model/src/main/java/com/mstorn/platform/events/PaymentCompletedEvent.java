package com.mstorn.platform.events;

import java.util.UUID;

public class PaymentCompletedEvent {

    private UUID orderId;

    public PaymentCompletedEvent() {
    }

    public PaymentCompletedEvent(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getOrderId() {
        return orderId;
    }
}