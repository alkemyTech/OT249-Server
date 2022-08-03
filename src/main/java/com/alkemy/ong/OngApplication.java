package com.alkemy.ong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class OngApplication {

	public sttic void main(String[] args) {
		SpringApplication.run(OngApplication.class, args);
		System.out.println("Running spring app");
	}

}
