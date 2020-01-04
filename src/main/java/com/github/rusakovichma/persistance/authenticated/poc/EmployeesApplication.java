package com.github.rusakovichma.persistance.authenticated.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class EmployeesApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(EmployeesApplication.class, args);
		run.registerShutdownHook();
	}

}
