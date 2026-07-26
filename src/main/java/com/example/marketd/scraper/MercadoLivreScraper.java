package com.example.marketd.scraper;

import com.example.marketd.domain.Vehicle;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class MercadoLivreScraper extends BaseScraper{

    @Override
    public boolean supports(String url) {
        return url != null && url.contains("mercadolivre.com.br");
    }

    @Override
    public Optional<Vehicle> extractData(String url, Document document) {
        try {
            Element titleElement = document.selectFirst("h1.ui-pdp-title");
            Element priceElement = document.selectFirst("span.andes-money-amount__fraction");

            if (titleElement == null || priceElement == null) {
                return Optional.empty(); // O layout mudou ou foi bloqueado
            }

            Vehicle vehicle = Vehicle.builder()
                    .title(titleElement.text())
                    .price(parsePrice(priceElement.text()))
                    .url(url)
                    .source("MERCADO_LIVRE")
                    .scraped_at(LocalDateTime.now())
                    .build();

            return Optional.of(vehicle);
        } catch (Exception e) {
            return Optional.empty();
        }
    } 
}
