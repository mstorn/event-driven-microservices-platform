package com.mstorn.platform.events;

import java.util.UUID;

public class InventoryReleasedEvent {

    private UUID orderId;

    public InventoryReleasedEvent() {}

    public InventoryReleasedEvent(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getOrderId() {
        return orderId;
    }
}