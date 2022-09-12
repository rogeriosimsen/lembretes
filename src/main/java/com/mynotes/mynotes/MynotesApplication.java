package com.mynotes.mynotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EntityScan(basePackages = {"com.mynote.model"})
@ComponentScan(basePackages = {"com.*"})
@EnableJpaRepositories(basePackages = {"com.mynote.repository"})
@EnableTransactionManagement
@RestController
@EnableWebMvc
@EnableAutoConfiguration
@EnableCaching
@SpringBootApplication
public class MynotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MynotesApplication.class, args);
	}

}
