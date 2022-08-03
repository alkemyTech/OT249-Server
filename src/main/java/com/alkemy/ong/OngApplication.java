package com.alkemy.ong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alkemy.ong.config.SwaggerConfiguration;

@SpringBootApplication()
public class OngApplication{

	public static void main(String[] args) {
		SpringApplication.run(OngApplication.class, args);
		System.out.println("Running spring app");
	}
	
}
