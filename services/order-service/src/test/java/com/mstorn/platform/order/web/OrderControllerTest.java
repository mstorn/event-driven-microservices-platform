package com.mstorn.platform.order.web;

import com.mstorn.platform.order.application.OrderService;
import com.mstorn.platform.order.config.SecurityTestConfig;
import com.mstorn.platform.order.domain.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@Import(SecurityTestConfig.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Test
    @WithMockUser
    void shouldAllowAccess_whenUserHasOrderWriteScope() throws Exception {

        Order mockOrder = new Order("Test Order", 5);

        when(orderService.createOrder(any(Order.class)))
                .thenReturn(mockOrder);

        mockMvc.perform(post("/orders")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_orders.write")))                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "description": "Test Order",
                          "quantity": 5
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Test Order"))
                .andExpect(jsonPath("$.quantity").value(5))
                .andExpect(jsonPath("$.status").value("CREATED"));
    }

    @Test
    @WithMockUser
    void shouldReturn403_whenAuthorityRoleMissing() throws Exception {

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
                .andExpect(status().isForbidden());
    }

    @Test
//    @WithMockUser
    void shouldReturn400_whenBodyMissing() throws Exception {
        mockMvc.perform(post("/orders")
                .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_orders.write")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void shouldReturn400_whenQuantityInvalid() throws Exception {
        mockMvc.perform(post("/orders")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_orders.write")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "description": "Test",
                      "quantity": 0
                    }
                    """))
                .andExpect(status().isBadRequest());
    }
}