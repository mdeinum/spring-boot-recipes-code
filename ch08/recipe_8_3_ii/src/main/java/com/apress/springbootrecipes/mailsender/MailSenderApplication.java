package com.apress.springbootrecipes.mailsender;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.Message;
import java.util.Collections;

@SpringBootApplication
public class MailSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailSenderApplication.class, args);
	}

	@Bean
	public ApplicationRunner startupMailSender(
			JavaMailSender mailSender,
			SpringTemplateEngine templateEngine) {
		return (args) -> {
			mailSender.send((msg) -> {
				MimeMessageHelper helper = new MimeMessageHelper(msg);
				helper.setTo("recipient@some.where");
				helper.setFrom("spring-boot-2-recipes@apress.com");
				helper.setSubject("Status message");

				IContext context =  new Context(
						LocaleContextHolder.getLocale(),
						Collections.singletonMap("msg", "All is well!"));
				String body =
						templateEngine.process("email.html", context);
				helper.setText(body, true);
			});
		};
	}
}
