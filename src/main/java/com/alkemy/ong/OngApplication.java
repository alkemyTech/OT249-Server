package com.alkemy.ong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// I add exclude annotation for run the app without DB config
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class OngApplication {

	public static void main(String[] args) {
		SpringApplication.run(OngApplication.class, args);
		System.out.println("Running spring app");
	}

}
