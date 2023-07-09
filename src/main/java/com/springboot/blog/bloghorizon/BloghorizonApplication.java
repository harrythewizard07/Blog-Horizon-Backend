package com.springboot.blog.bloghorizon;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "BlogHorizon Blogging Application REST APIs",
				description = "Spring Boot Blogging Application REST APIs Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Harshit Joshi",
						email = "harshitjoshi67@gmail.com"
				)
		)
)
public class BloghorizonApplication {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BloghorizonApplication.class, args);
		
		log.info("Bloghorizon application started!");
	}

}