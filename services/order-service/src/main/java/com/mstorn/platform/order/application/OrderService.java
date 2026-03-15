package com.mstorn.platform.order.application;

import com.mstorn.platform.events.*;
import com.mstorn.platform.order.application.port.OrderEventPublisher;
import com.mstorn.platform.order.domain.model.Order;
import com.mstorn.platform.order.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderEventPublisher eventPublisher;

    private OrderRepository orderRepository;

    public OrderService(OrderEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        this.orderRepository = new OrderRepository();
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

        Order order = orderRepository.findById(event.getOrderId());

        order.markPaymentCompleted();
        order.complete();

    }

    @KafkaListener(
            topics = KafkaTopics.PAYMENT_FAILED,
            groupId = "order-group"
    )
    public void handle(PaymentFailedEvent event) {

        Order order = orderRepository.findById(event.getOrderId());

        order.cancel();

    }

    @KafkaListener(
            topics = KafkaTopics.INVENTORY_RELEASED,
            groupId = "order-group"
    )
    public void handle(InventoryReleasedEvent event) {

        Order order = orderRepository.findById(event.getOrderId());

        order.cancel();

    }


}