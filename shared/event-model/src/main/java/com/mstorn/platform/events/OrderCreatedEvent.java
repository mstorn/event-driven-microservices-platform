package com.mstorn.platform.events;

import java.time.Instant;
import java.util.UUID;

public class OrderCreatedEvent {

    private UUID eventId;
    private UUID orderId;
    private int quantity;
    private String description;
    private Instant createdAt;

    public OrderCreatedEvent(UUID orderId, String description, int quantity) {
        this.orderId = orderId;
        this.description = description;
        this.quantity = quantity;
        this.createdAt = Instant.now();
        this.eventId = UUID.randomUUID();
    }

    public OrderCreatedEvent() {
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

    public UUID getEventId() {
        return eventId;
    }
}