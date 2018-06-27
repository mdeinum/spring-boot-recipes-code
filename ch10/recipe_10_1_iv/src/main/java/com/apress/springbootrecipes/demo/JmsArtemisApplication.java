package com.apress.springbootrecipes.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Marten Deinum
 */
@SpringBootApplication
public class JmsArtemisApplication {

    private static final String MSG = "\tName: %100s, Type: %s\n";

    public static void main(String[] args) {
        ApplicationContext ctx =
                SpringApplication.run(JmsArtemisApplication.class, args);

        System.out.println("# Beans: " + ctx.getBeanDefinitionCount());

        String[] names = ctx.getBeanDefinitionNames();
        for (String name : names) {
            Object bean = ctx.getBean(name);
            System.out.printf(MSG, name, bean.getClass().getSimpleName());
        }
    }
}
