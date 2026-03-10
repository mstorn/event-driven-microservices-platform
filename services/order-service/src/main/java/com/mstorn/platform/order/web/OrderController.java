package com.mstorn.platform.order.web;

import com.mstorn.platform.order.domain.model.Order;
import com.mstorn.platform.order.application.OrderService;
import com.mstorn.platform.order.dto.CreateOrderRequest;
import com.mstorn.platform.order.dto.OrderResponse;
import com.mstorn.platform.order.mapper.OrderMapper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasRole('ORDER_WRITER')")
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request
    ) {
        log.info("HTTP create order: description='{}'", request.description());

        Order order = OrderMapper.toDomain(request);
        Order created = orderService.createOrder(order);

        return ResponseEntity.ok(OrderMapper.toResponse(created));
    }}