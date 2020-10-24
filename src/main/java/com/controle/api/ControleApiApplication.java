package com.controle.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

import com.controle.api.config.property.ControleApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(ControleApiProperty.class)
public class ControleApiApplication {
	
	private static ApplicationContext APPLICATION_CONTEXT;
	

	public static void main(String[] args) {
		APPLICATION_CONTEXT = SpringApplication.run(ControleApiApplication.class, args);
	}
	
	public static <T> T getBean(Class<T> type) {
		return APPLICATION_CONTEXT.getBean(type);
	}

}
