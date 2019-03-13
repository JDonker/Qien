package com.qien;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.qien.controller.GebruikerService;

@SpringBootApplication
public class QienApplication {

	public static void main(String[] args) {
		SpringApplication.run(QienApplication.class, args);
	}
	
    @Bean
    CommandLineRunner init(GebruikerService gebruikerService) {
        return (args) -> gebruikerService.initGebruikers();
    }

}
