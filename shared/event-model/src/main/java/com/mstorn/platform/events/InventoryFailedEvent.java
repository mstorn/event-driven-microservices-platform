package com.mstorn.platform.events;

import java.util.UUID;

public class InventoryFailedEvent {

    private UUID orderId;
    private String reason;

    public InventoryFailedEvent() {}

    public InventoryFailedEvent(UUID orderId, String reason) {
        this.orderId = orderId;
        this.reason = reason;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public String getReason() {
        return reason;
    }
}