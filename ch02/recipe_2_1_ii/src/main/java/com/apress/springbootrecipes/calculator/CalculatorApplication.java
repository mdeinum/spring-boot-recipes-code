package com.apress.springbootrecipes.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CalculatorApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext ctx =
				SpringApplication.run(CalculatorApplication.class, args);

		Calculator calculator = ctx.getBean(Calculator.class);
		calculator.calculate(137, 21, '+');
		calculator.calculate(137, 21, '*');

		// This will throw an exception as there is no suitable operation
		calculator.calculate(137, 21, '-');
	}

	@Bean
	public Calculator calculator(Iterable<Operation> operations) {
		return new Calculator(operations);
	}
}
