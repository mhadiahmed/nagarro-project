package com.nagarro.nagarroproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class NagarroProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(NagarroProjectApplication.class, args);
	}

	@GetMapping
	public String hello(){
		return "hello world";
	}

}
