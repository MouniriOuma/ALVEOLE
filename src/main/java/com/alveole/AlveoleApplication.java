package com.alveole;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@SpringBootApplication
public class AlveoleApplication implements WebMvcConfigurer {
	
	  @Override
	    public void addCorsMappings(CorsRegistry registry) {
		  //clients mapping
	        registry.addMapping("/clients/**")
	                .allowedOrigins("http://localhost:3000")
	                .allowedMethods("GET", "POST", "PUT", "DELETE")
	                .allowedHeaders("Content-Type", "Authorization");
	        
	      //bills mapping
	        registry.addMapping("/bills/**")
		            .allowedOrigins("http://localhost:3000")
		            .allowedMethods("GET", "POST", "PUT", "DELETE")
		            .allowedHeaders("Content-Type", "Authorization");
	        
	      //ingredients mapping 
	        registry.addMapping("/ingredients/**")
		            .allowedOrigins("http://localhost:3000")
		            .allowedMethods("GET", "POST", "PUT", "DELETE")
		            .allowedHeaders("Content-Type", "Authorization");
	        
	      //products mapping
	        registry.addMapping("/products/**")
		            .allowedOrigins("http://localhost:3000")
		            .allowedMethods("GET", "POST", "PUT", "DELETE")
		            .allowedHeaders("Content-Type", "Authorization");
	        
	      //suppliers mapping
	        registry.addMapping("/suppliers/**")
		            .allowedOrigins("http://localhost:3000")
		            .allowedMethods("GET", "POST", "PUT", "DELETE")
		            .allowedHeaders("Content-Type", "Authorization");
	     
	      // users mapping 
			registry.addMapping("/users/**")
					.allowedOrigins("http://localhost:3000")
					.allowedMethods("GET", "POST", "PUT", "DELETE")
					.allowedHeaders("Content-Type", "Authorization");
			
	      // waterElecs mapping 
			registry.addMapping("/waterElecs/**")
					.allowedOrigins("http://localhost:3000")
					.allowedMethods("GET", "POST", "PUT", "DELETE")
					.allowedHeaders("Content-Type", "Authorization");
	    }
	public static void main(String[] args) {
		SpringApplication.run(AlveoleApplication.class, args);
	}

}
