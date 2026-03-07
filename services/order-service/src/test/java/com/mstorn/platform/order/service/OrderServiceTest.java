package com.mstorn.platform.order.service;

import com.mstorn.platform.order.application.OrderService;
import com.mstorn.platform.order.domain.event.OrderCreatedEvent;
import com.mstorn.platform.order.domain.model.Order;
import com.mstorn.platform.order.application.port.OrderEventPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class OrderServiceTest {

    private OrderEventPublisher publisher;
    private OrderService service;

    @BeforeEach
    void setup() {
        publisher = mock(OrderEventPublisher.class);
        service = new OrderService(publisher);
    }

    @Test
    void shouldCreateOrder() {
        Order order = new Order("Laptop", 2);

        Order created = service.createOrder(order);

        assertNotNull(created.getId());
        assertEquals("Laptop", created.getDescription());
        assertEquals(2, created.getQuantity());

        verify(publisher).publishOrderCreated(any(OrderCreatedEvent.class));
    }
}