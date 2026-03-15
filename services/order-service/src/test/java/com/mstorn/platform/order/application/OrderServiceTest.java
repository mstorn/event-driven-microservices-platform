package com.mstorn.platform.order.application;

import com.mstorn.platform.events.OrderCreatedEvent;
import com.mstorn.platform.order.domain.model.Order;
import com.mstorn.platform.order.application.port.OrderEventPublisher;
import com.mstorn.platform.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class OrderServiceTest {

    private OrderEventPublisher publisher;
    private OrderService service;
    private OrderRepository repository;

    @BeforeEach
    void setup() {
        publisher = mock(OrderEventPublisher.class);
        service = new OrderService(publisher);
        repository = mock(OrderRepository.class);
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