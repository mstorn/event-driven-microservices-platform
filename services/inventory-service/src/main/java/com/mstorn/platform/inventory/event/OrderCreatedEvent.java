package com.mstorn.platform.inventory.event;

import java.util.UUID;

public record OrderCreatedEvent(
        UUID orderId,
        String description,
        int quantity
) {}