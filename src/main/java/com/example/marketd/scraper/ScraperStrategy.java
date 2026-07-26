package com.example.marketd.scraper;

import com.example.marketd.domain.Vehicle;
import org.jsoup.nodes.Document;
import java.util.Optional;

public interface ScraperStrategy {
    // A cada estratégia o sistema pergunta: "Você sabe lidar com este link?"
    boolean supports(String url);

    // Se a resposta for true, o sistema manda a estratégia extrair os dados
    Optional<Vehicle> extractData(String url, Document document);
}
