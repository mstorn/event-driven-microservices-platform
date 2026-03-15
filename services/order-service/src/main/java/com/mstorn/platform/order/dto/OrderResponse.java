package com.mstorn.platform.order.dto;

import com.mstorn.platform.order.domain.model.OrderStatus;

import java.util.UUID;

public record OrderResponse(
        UUID id,
        String description,
        Integer quantity,
        OrderStatus status
) {}