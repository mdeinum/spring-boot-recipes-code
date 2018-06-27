package com.apress.springbootrecipes.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.test.TestRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitSenderApplicationTest {

	@Autowired
	private TestRabbitTemplate testRabbitTemplate;

	@Test
	public void shouldReceiveOrderPlain() throws Exception {

		Message message = testRabbitTemplate.receive("orders");

		ObjectMapper mapper = new ObjectMapper();
		Order order = mapper.readValue( message.getBody(), Order.class);

		System.out.println(order);

		assertThat(order).hasNoNullFieldsOrProperties();
	}

	@Test
	public void shouldReceiveOrderWithConversion() throws Exception {

		Order order = testRabbitTemplate.receiveAndConvert("orders", 1200, ParameterizedTypeReference.forType(Order.class));
		System.out.println(order);

		assertThat(order).hasNoNullFieldsOrProperties();
	}
}
