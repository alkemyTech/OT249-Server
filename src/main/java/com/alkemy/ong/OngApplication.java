package com.alkemy.ong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alkemy.ong.config.SwaggerConfiguration;

@SpringBootApplication()
@Import(SwaggerConfiguration.class)
public class OngApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(OngApplication.class, args);
		System.out.println("Running spring app");
	}
	
	   @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	 
	           registry.addResourceHandler("swagger-ui.html")
	                    .addResourceLocations("classpath:/META-INF/resources/");
	 
	    }
}
