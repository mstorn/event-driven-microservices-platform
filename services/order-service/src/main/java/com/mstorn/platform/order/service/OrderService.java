package com.mstorn.platform.order.service;

import com.mstorn.platform.order.domain.Order;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    public Order createOrder(String product, int quantity) {
        return new Order(UUID.randomUUID(), product, quantity);
    }
}