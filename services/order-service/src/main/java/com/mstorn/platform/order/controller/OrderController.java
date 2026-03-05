package com.mstorn.platform.order.controller;

import com.mstorn.platform.order.domain.Order;
import com.mstorn.platform.order.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order createOrder(@RequestParam String product,
                             @RequestParam int quantity) {
        return orderService.createOrder(product, quantity);
    }
}