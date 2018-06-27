package com.apress.springbootrecipes.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.stream.Stream;

/**
 * @author Marten Deinum
 */
@SpringBootApplication
public class JmsActiveMQApplication {

	private static final String MSG = "\tName: %100s, Type: %s\n";

	public static void main(String[] args) {
		ApplicationContext ctx =
						SpringApplication.run(JmsActiveMQApplication.class, args);

		System.out.println("# Beans: " + ctx.getBeanDefinitionCount());


		String[] names = ctx.getBeanDefinitionNames();
		Stream.of(names)
						.filter(name -> name.toLowerCase().contains("jms") || ctx.getType(name).getName().contains("jms"))
						.forEach(name -> {
							Object bean = ctx.getBean(name);
							System.out.printf(MSG, name, bean.getClass().getSimpleName());
						});
	}
}
