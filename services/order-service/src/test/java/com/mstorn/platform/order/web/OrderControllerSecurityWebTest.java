package com.mstorn.platform.order.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mstorn.platform.order.application.OrderService;
import com.mstorn.platform.order.config.SecurityTestConfig;
import com.mstorn.platform.order.domain.model.Order;
import com.mstorn.platform.order.dto.CreateOrderRequest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = OrderController.class,
        useDefaultFilters = false
)
@Import({
        OrderController.class,
        SecurityTestConfig.class
})
class OrderControllerSecurityWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderService orderService;

    // =============================
    // Helper
    // =============================

    private String validBody() throws Exception {
        return objectMapper.writeValueAsString(
                new CreateOrderRequest("Test Order", 1)
        );
    }

    private Order mockOrder() {
        return new Order(
                "Test Order",
                1
        );
    }

    // =============================
    // Authentication Tests
    // =============================

    @Nested
    class AuthenticationTests {

        @Test
        void shouldReturn403_whenUnauthenticated() throws Exception {

            mockMvc.perform(post("/orders")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(validBody()))
                    .andExpect(status().isForbidden());
        }
    }

    // =============================
    // Authorization Tests
    // =============================

    @Nested
    class AuthorizationTests {

        @Test
        @WithMockUser(roles = {})
        void shouldReturn403_whenUserHasNoRole() throws Exception {

            Mockito.when(orderService.createOrder(any()))
                    .thenReturn(mockOrder());

            mockMvc.perform(post("/orders")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(validBody()))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(roles = "USER")
        void shouldAllowAccessForUserRole() throws Exception {

            Mockito.when(orderService.createOrder(any()))
                    .thenReturn(mockOrder());

            mockMvc.perform(post("/orders")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(validBody()))
                    .andExpect(status().isOk());
        }
    }

    // =============================
    // Validation Tests
    // =============================

    @Nested
    class ValidationTests {

        @Test
        @WithMockUser(roles = "USER")
        void shouldReturn400_whenBodyMissing() throws Exception {
            mockMvc.perform(post("/orders")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @WithMockUser(roles = "USER")
        void shouldReturn400_whenQuantityInvalid() throws Exception {
            String invalidBody = """
                    {
                      "description": "Test Order",
                      "quantity": 0
                    }
                    """;

            mockMvc.perform(post("/orders")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(invalidBody))
                    .andExpect(status().isBadRequest());
        }
    }
}