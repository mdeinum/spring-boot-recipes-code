package com.apress.springboot2recipes.jpa;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.Arrays;

@SpringBootApplication
public class HibernateApplication {

	public static void main(String[] args) {
		SpringApplication.run(HibernateApplication.class, args);
	}

	@Bean
	public BeanFactoryPostProcessor hibernateEntityManagerFactoryPostProcessor() {
		return beanFactory -> {
			BeanDefinition beanDefinition = beanFactory.getBeanDefinition("entityManagerFactory");
			beanDefinition.getPropertyValues().add("entityManagerFactoryInterface", SessionFactory.class);
			beanDefinition.getPropertyValues().add("entityManagerInterface", Session.class);
		};
	}
}

@Component
class CustomerLister implements ApplicationRunner {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final CustomerRepository customers;

	CustomerLister(CustomerRepository customers) {
		this.customers = customers;
	}

	@Override
	public void run(ApplicationArguments args) {

		customers.findAll().forEach( customer -> logger.info("{}", customer));
	}
}
