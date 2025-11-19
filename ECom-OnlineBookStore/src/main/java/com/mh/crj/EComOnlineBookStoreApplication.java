package com.mh.crj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
		info = @Info(
				title = "Ecommerce Book Store Management",
				version = "1.0",
				description = "Welcome to the Chaitanya's ECom website",
				contact = @Contact(name = "Chaitanya Jagtap",email = "chaitanyajagtap531@gmail.com")
				)
		)


@SpringBootApplication
@EnableCaching
public class EComOnlineBookStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(EComOnlineBookStoreApplication.class, args);
	}

	
	
//  Security Configuration Bean
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/saveBook").permitAll() // Allow POST /save
//                // post put delete method request path we should method to allow them to execute without authentication
//                .requestMatchers("/greetmsg", "/greetmsg2").authenticated() // Require login
//                .anyRequest().permitAll() // Other routes open
//            )
//            .oauth2Login() // Enable GitHub & LinkedIn OAuth2 login
//            .and()
//            // when we apply the oauth the swagger will enable csrf token for post put delete request
//            .csrf().disable(); // Disable CSRF for testing via Swagger/Postman
////csrf--->Cross-Site Request Forgery
//        return http.build();
//    }
	
}
