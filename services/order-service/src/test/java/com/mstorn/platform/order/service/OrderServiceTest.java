package com.mstorn.platform.order.service;

import com.mstorn.platform.order.domain.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private final OrderService orderService = new OrderService();

    @Test
    void shouldCreateOrder() {

        Order order = orderService.createOrder("Laptop", 2);

        assertNotNull(order.getId());
        assertEquals("Laptop", order.getProduct());
        assertEquals(2, order.getQuantity());
    }
}