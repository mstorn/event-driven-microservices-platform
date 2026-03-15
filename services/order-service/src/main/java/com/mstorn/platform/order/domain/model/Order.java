package com.mstorn.platform.order.domain.model;

import java.util.UUID;

public class Order {

    private final UUID id;
    private final String description;
    private final int quantity;
    private OrderStatus status;

    public Order(String description, int quantity) {
        this.id = UUID.randomUUID();
        this.description = description;
        this.quantity = quantity;
        this.status = OrderStatus.CREATED;
    }

    public void markInventoryReserved() {
        this.status = OrderStatus.INVENTORY_RESERVED;
    }

    public void markPaymentCompleted() {
        this.status = OrderStatus.PAYMENT_COMPLETED;
    }

    public void complete() {
        this.status = OrderStatus.COMPLETED;
    }

    public void cancel() {
        this.status = OrderStatus.CANCELLED;
    }    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderStatus getStatus() {
        return status;
    }
}