package com.mstorn.platform.events;

import java.util.UUID;

public class InventoryReservedEvent {

    private  UUID orderId;

    public InventoryReservedEvent() {
    }

    public InventoryReservedEvent(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getOrderId() {
        return orderId;
    }
}