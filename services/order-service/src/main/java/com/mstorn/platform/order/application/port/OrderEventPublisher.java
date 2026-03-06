package com.mstorn.platform.order.application.port;

import com.mstorn.platform.order.domain.event.OrderCreatedEvent;

public interface OrderEventPublisher {

    void publishOrderCreated(OrderCreatedEvent event);

}