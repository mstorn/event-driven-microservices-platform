package com.mstorn.platform.order.domain.model;

import java.util.UUID;

public class Order {

    private final UUID id;
    private final String description;
    private final int quantity;

    public Order(String description, int quantity) {
        this.id = UUID.randomUUID();
        this.description = description;
        this.quantity = quantity;
    }
    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }
}