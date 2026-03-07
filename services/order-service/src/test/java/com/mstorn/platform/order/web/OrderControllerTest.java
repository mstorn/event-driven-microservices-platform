package com.mstorn.platform.order.web;

import com.mstorn.platform.order.application.OrderService;
import com.mstorn.platform.order.domain.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Test
    void shouldCreateOrder() throws Exception {

        Order mockOrder = new Order("Test Order", 5);

        when(orderService.createOrder(any(Order.class)))
                .thenReturn(mockOrder);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "description": "Test Order",
                          "quantity": 5
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Test Order"))
                .andExpect(jsonPath("$.quantity").value(5));
    }

    @Test
    void shouldReturn400_whenBodyMissing() throws Exception {
        mockMvc.perform(post("/orders"))
                .andExpect(status().isBadRequest());
    }
}