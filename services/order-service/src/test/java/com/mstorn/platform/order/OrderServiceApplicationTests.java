package com.mstorn.platform.order;

import com.mstorn.platform.order.application.port.OrderEventPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class OrderServiceApplicationTests {

	@MockitoBean
	private OrderEventPublisher orderEventPublisher;

	@Test
	void contextLoads() {
	}

}
