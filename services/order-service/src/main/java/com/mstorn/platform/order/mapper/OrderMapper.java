package com.mstorn.platform.order.mapper;

import com.mstorn.platform.order.domain.model.Order;
import com.mstorn.platform.order.dto.CreateOrderRequest;
import com.mstorn.platform.order.dto.OrderResponse;

public class OrderMapper {

    public static Order toDomain(CreateOrderRequest request) {
        return new Order(request.description(), request.quantity());
    }

    public static OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getDescription(),
                order.getQuantity(),
                order.getStatus()
        );
    }
}