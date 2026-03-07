package com.mstorn.platform.order.application;

import com.mstorn.platform.order.domain.model.Order;
import com.mstorn.platform.order.domain.event.OrderCreatedEvent;
import com.mstorn.platform.order.application.port.OrderEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderEventPublisher eventPublisher;

    public OrderService(OrderEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public Order createOrder(String description, int quantity) {

        log.info("Creating order with description='{}'", description);
        Order order = new Order(description, quantity);

        OrderCreatedEvent event =
                new OrderCreatedEvent(order.getId(), order.getDescription(), order.getQuantity());
        log.info("Order created: id={}, description='{}'",
                order.getId(),
                order.getDescription());

        eventPublisher.publishOrderCreated(event);
        log.info("OrderCreatedEvent published for orderId={}", order.getId());

        return order;
    }
}