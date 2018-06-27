package com.apress.springbootrecipes.demo;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Marten Deinum
 */
@SpringBootApplication
@EnableScheduling
public class RabbitSenderApplication {

    public static void main(String[] args) {
			SpringApplication.run(RabbitSenderApplication.class, args);
    }
}


@Component
class OrderSender {


	private final RabbitTemplate rabbit;

	OrderSender(RabbitTemplate rabbit) {
		this.rabbit = rabbit;
	}

	@Scheduled(fixedRate = 500)
	public void sendTime() {

		String id = UUID.randomUUID().toString();
		double amount = ThreadLocalRandom.current().nextDouble(1000.00d);
		Order order = new Order(id, BigDecimal.valueOf(amount));
		rabbit.convertAndSend("orders", order);
	}

}
