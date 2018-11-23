package com.vipul;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.vipul.service.PersonService;

@EnableJpaRepositories
@EnableTransactionManagement
@SpringBootApplication
public class SpringTransactionPropagationExamplesApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication
				.run(SpringTransactionPropagationExamplesApplication.class, args);
		PersonService bean = context.getBean(PersonService.class);
		bean.callTransactionalMethods();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
