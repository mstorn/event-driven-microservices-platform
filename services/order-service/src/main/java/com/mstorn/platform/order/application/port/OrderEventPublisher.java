package com.mstorn.platform.order.application.port;

import com.mstorn.platform.events.OrderCreatedEvent;

public interface OrderEventPublisher {

    void publishOrderCreated(OrderCreatedEvent event);

}