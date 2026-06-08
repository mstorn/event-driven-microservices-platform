package com.mstorn.platform.order.application;

import com.mstorn.platform.events.*;
import com.mstorn.platform.order.application.port.OrderEventPublisher;
import com.mstorn.platform.order.domain.model.Order;
import com.mstorn.platform.order.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderEventPublisher eventPublisher;
    private final OrderRepository orderRepository;

    public OrderService(OrderEventPublisher eventPublisher, OrderRepository orderRepository) {
        this.eventPublisher = eventPublisher;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {

        log.info("Creating order {}", order.getId());

        orderRepository.save(order);

        OrderCreatedEvent event =
                new OrderCreatedEvent(order.getId(), order.getDescription(), order.getQuantity());
        log.info("Order created: id={}, description='{}'",
                order.getId(),
                order.getDescription());

        eventPublisher.publishOrderCreated(event);
        log.info("OrderCreatedEvent published for orderId={}", order.getId());

        return order;
    }

    @KafkaListener(
            topics = KafkaTopics.PAYMENT_COMPLETED,
            groupId = "order-group"
    )
    public void handle(PaymentCompletedEvent event) {

        Order order = findOrder(event.getOrderId());

        // Two-step transition keeps the domain state progression explicit.
        order.markPaymentCompleted();
        order.complete();

    }

    @KafkaListener(
            topics = KafkaTopics.PAYMENT_FAILED,
            groupId = "order-group"
    )
    public void handle(PaymentFailedEvent event) {

        cancelOrder(event.getOrderId());

    }

    @KafkaListener(
            topics = KafkaTopics.INVENTORY_RELEASED,
            groupId = "order-group"
    )
    public void handle(InventoryReleasedEvent event) {

        cancelOrder(event.getOrderId());

    }

    private Order findOrder(UUID orderId) {
        return orderRepository.findById(orderId);
    }

    private void cancelOrder(UUID orderId) {
        Order order = findOrder(orderId);
        order.cancel();
    }
}
