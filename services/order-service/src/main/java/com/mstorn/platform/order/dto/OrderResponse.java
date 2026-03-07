package com.mstorn.platform.order.dto;

import java.util.UUID;

public record OrderResponse(
        UUID id,
        String description,
        Integer quantity
) {}