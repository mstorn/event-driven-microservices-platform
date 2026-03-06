package com.mstorn.platform.order.application;

import com.mstorn.platform.order.domain.model.Order;
import com.mstorn.platform.order.domain.event.OrderCreatedEvent;
import com.mstorn.platform.order.application.port.OrderEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderEventPublisher eventPublisher;

    public OrderService(OrderEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public Order createOrder(String description, int quantity) {
        Order order = new Order(description, quantity);

        OrderCreatedEvent event =
                new OrderCreatedEvent(order.getId(), order.getDescription(), order.getQuantity());

        eventPublisher.publishOrderCreated(event);

        return order;
    }
}