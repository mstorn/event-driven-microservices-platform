package com.mstorn.platform.order.domain.event;

import java.time.Instant;
import java.util.UUID;

public class OrderCreatedEvent {

    private final UUID orderId;
    private final int quantity;
    private final String description;
    private final Instant createdAt;

    public OrderCreatedEvent(UUID orderId, String description, int quantity) {
        this.orderId = orderId;
        this.description = description;
        this.quantity = quantity;
        this.createdAt = Instant.now();
    }

    public UUID getOrderId() {
        return orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getDescription() {
        return description;
    }
}