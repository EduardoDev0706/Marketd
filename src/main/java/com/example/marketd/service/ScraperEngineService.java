package com.example.marketd.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.example.marketd.event.VehicleScrapedEvent;
import com.example.marketd.scraper.ScraperStrategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScraperEngineService {

    private final List<ScraperStrategy> strategies;
    private final ApplicationEventPublisher eventPublisher;

    public void processUrl(String url) {
        log.info("Iniciando processamento da URL: {}", url);

        strategies.stream()
                .filter(strategy -> strategy.supports(url))
                .findFirst()
                .ifPresentOrElse(
                    strategy -> executeScraping(strategy, url),
                () -> log.warn("Nenhuma estratégia encontrada para a URL: {}", url)
            );
    }

    private void executeScraping(ScraperStrategy strategy, String url) {
        try {
            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .timeout(10000)
                    .get();

            strategy.extractData(url, document).ifPresent(vehicle -> {
                log.info("Sucesso! Veículo extraido: {} - R$ {}", vehicle.getTitle(), vehicle.getPrice());

                // O Scraper não salva no banco, apenas dispara o evento
                eventPublisher.publishEvent(new VehicleScrapedEvent(vehicle));
            });
        } catch (Exception e) {
            log.error("Falha ao extrair dados da URL {}: {}", url, e.getMessage());
        }
    }
}
