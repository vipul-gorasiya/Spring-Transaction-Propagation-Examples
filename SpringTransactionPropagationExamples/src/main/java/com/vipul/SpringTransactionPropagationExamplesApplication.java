package com.vipul;

import com.vipul.service.PersonService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories
@EnableTransactionManagement
@SpringBootApplication
public class SpringTransactionPropagationExamplesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringTransactionPropagationExamplesApplication.class, args);
		// Run Test Cases
	}
}
