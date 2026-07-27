package com.example.marketd;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.marketd.service.ScraperEngineService;

@SpringBootApplication
@EnableScheduling
public class MarketdApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketdApplication.class, args);
	}

}
