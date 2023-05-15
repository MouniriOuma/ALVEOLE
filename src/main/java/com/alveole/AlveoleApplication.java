package com.alveole;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@SpringBootApplication
public class AlveoleApplication implements WebMvcConfigurer {
	
	  @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/clients/**")
	                .allowedOrigins("http://localhost:3000")
	                .allowedMethods("GET", "POST", "PUT", "DELETE")
	                .allowedHeaders("Content-Type", "Authorization");
	    }
	public static void main(String[] args) {
		SpringApplication.run(AlveoleApplication.class, args);
	}

}
