package com.example.marketd;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.marketd.service.ScraperEngineService;

@SpringBootApplication
public class MarketdApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketdApplication.class, args);
	}

	@Bean
	public CommandLineRunner testRunner(ScraperEngineService engineService) {
		return args -> {
			engineService.processUrl("https://moto.mercadolivre.com.br/MLB-4810869325-honda-xre-300-_JM#polycard_client=search-desktop&price_drop=not_apply&be_origin=backend&overlay_label=not_apply&search_layout=grid&position=1&type=item&tracking_id=f03d2e82-a773-458b-967e-596349497228&sid=search");
		};
	}

}
