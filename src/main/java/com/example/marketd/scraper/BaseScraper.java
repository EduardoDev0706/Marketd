package com.example.marketd.scraper;

import com.example.marketd.domain.Vehicle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;


public abstract class BaseScraper implements ScraperStrategy{

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";
    private static final int TIMEOUT_MS = 10000;

    // Método exposto que a classe concreta vai chamar
    protected Document fetchDocument(String url) throws IOException {
        return Jsoup.connect(url)
                .userAgent(USER_AGENT)
                .header("Accept-Language", "pt-BR,pt;q=0.9")
                .timeout(TIMEOUT_MS)
                .get();
    }

    // Métodos auxiliares para limpar dados sujos
    protected BigDecimal parsePrice(String rawPrice) {
        if (rawPrice == null || rawPrice.isBlank()) return BigDecimal.ZERO;
        // Pega "R$10.000" e transforma em "10000"
        String cleanNumber = rawPrice.replaceAll("[^0-9]", "");
        return new BigDecimal(cleanNumber);
    }

    // A classe concreta é obrigada a implementar esse método (Template Method)
    @Override
    public abstract Optional<Vehicle> extractData(String url, Document document);


}
