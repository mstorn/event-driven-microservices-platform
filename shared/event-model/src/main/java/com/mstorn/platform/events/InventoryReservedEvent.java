package com.mstorn.platform.events;

import java.util.UUID;

public class InventoryReservedEvent {

    private UUID eventId;
    private UUID orderId;

    public InventoryReservedEvent() {}

    public InventoryReservedEvent(UUID orderId) {
        this.eventId = UUID.randomUUID();
        this.orderId = orderId;
    }

    public UUID getEventId() {
        return eventId;
    }

    public UUID getOrderId() {
        return orderId;
    }
}