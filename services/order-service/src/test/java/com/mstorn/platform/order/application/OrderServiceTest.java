package com.mstorn.platform.order.application;

import com.mstorn.platform.events.OrderCreatedEvent;
import com.mstorn.platform.events.InventoryReleasedEvent;
import com.mstorn.platform.events.PaymentCompletedEvent;
import com.mstorn.platform.events.PaymentFailedEvent;
import com.mstorn.platform.order.application.port.OrderEventPublisher;
import com.mstorn.platform.order.domain.model.Order;
import com.mstorn.platform.order.domain.model.OrderStatus;
import com.mstorn.platform.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private CapturingOrderEventPublisher publisher;
    private OrderService service;

    @BeforeEach
    void setup() {
        publisher = new CapturingOrderEventPublisher();
        service = new OrderService(publisher, new OrderRepository());
    }

    @Test
    void shouldCreateOrder() {
        Order order = new Order("Laptop", 2);

        Order created = service.createOrder(order);

        assertNotNull(created.getId());
        assertEquals("Laptop", created.getDescription());
        assertEquals(2, created.getQuantity());
        assertEquals(OrderStatus.CREATED, created.getStatus());

        OrderCreatedEvent publishedEvent = publisher.lastPublishedOrderCreatedEvent;
        assertNotNull(publishedEvent);
        assertEquals(order.getId(), publishedEvent.getOrderId());
        assertEquals(order.getDescription(), publishedEvent.getDescription());
        assertEquals(order.getQuantity(), publishedEvent.getQuantity());
        assertNotNull(publishedEvent.getEventId());
        assertNotNull(publishedEvent.getCreatedAt());
    }

    @Test
    void shouldMarkOrderAsCompletedOnPaymentCompletedEvent() {
        Order order = new Order("Laptop", 2);
        service.createOrder(order);

        service.handle(new PaymentCompletedEvent(order.getId()));

        assertEquals(OrderStatus.COMPLETED, order.getStatus());
    }

    @Test
    void shouldCancelOrderOnPaymentFailedEvent() {
        Order order = new Order("Laptop", 2);
        service.createOrder(order);

        service.handle(new PaymentFailedEvent(order.getId(), "Payment rejected"));

        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    void shouldCancelOrderOnInventoryReleasedEvent() {
        Order order = new Order("Laptop", 2);
        service.createOrder(order);

        service.handle(new InventoryReleasedEvent(order.getId()));

        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    void shouldThrowWhenPaymentCompletedEventTargetsUnknownOrder() {
        UUID unknownOrderId = UUID.randomUUID();

        assertThrows(NullPointerException.class,
                () -> service.handle(new PaymentCompletedEvent(unknownOrderId)));
    }

    @Test
    void shouldThrowWhenPaymentFailedEventTargetsUnknownOrder() {
        UUID unknownOrderId = UUID.randomUUID();

        assertThrows(NullPointerException.class,
                () -> service.handle(new PaymentFailedEvent(unknownOrderId, "Payment rejected")));
    }

    @Test
    void shouldThrowWhenInventoryReleasedEventTargetsUnknownOrder() {
        UUID unknownOrderId = UUID.randomUUID();

        assertThrows(NullPointerException.class,
                () -> service.handle(new InventoryReleasedEvent(unknownOrderId)));
    }

    @Test
    void shouldPublishOneOrderCreatedEventPerCreatedOrder() {
        Order first = new Order("Laptop", 2);
        Order second = new Order("Mouse", 1);

        service.createOrder(first);
        service.createOrder(second);

        assertEquals(2, publisher.publishedOrderCreatedEvents.size());
        assertEquals(first.getId(), publisher.publishedOrderCreatedEvents.get(0).getOrderId());
        assertEquals(second.getId(), publisher.publishedOrderCreatedEvents.get(1).getOrderId());
    }

    private static class CapturingOrderEventPublisher implements OrderEventPublisher {
        private OrderCreatedEvent lastPublishedOrderCreatedEvent;
        private final List<OrderCreatedEvent> publishedOrderCreatedEvents = new ArrayList<>();

        @Override
        public void publishOrderCreated(OrderCreatedEvent event) {
            this.lastPublishedOrderCreatedEvent = event;
            this.publishedOrderCreatedEvents.add(event);
        }
    }
}
