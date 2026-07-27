package com.example.marketd.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarketSchedulerService {
    
    private final ScraperEngineService engineService;
    private final Random random = new Random();

    // Lista de URLs que vão ser monitoradas
    private final List<String> targetUrls = List.of(
        "https://moto.mercadolivre.com.br/MLB-4830908537-boulevard-m800-com-manual-do-proprietario-e-chave-reserva-_JM#polycard_client=search-desktop&price_drop=not_apply&be_origin=backend&overlay_label=not_apply&search_layout=grid&position=9&type=item&tracking_id=f03d2e82-a773-458b-967e-596349497228&sid=search",
        "https://moto.mercadolivre.com.br/MLB-4810869325-honda-xre-300-_JM#polycard_client=search-desktop&price_drop=not_apply&be_origin=backend&overlay_label=not_apply&search_layout=grid&position=1&type=item&tracking_id=f03d2e82-a773-458b-967e-596349497228&sid=search"
    );

    // O cron job roda a cada 4 horas.
    // "0 0 */4 * * *" significa: no minuto 0, da hora 0, a cada 4 horas.
    @Scheduled(fixedDelay = 60000)
    public void executeScheduledScraping() {
        log.info("Iniciando rotina de raspagem agendada...");

        for (String url : targetUrls) {
            // Jittering, mudanças aleatórias de acesso 
            int jitterSeconds = random.nextInt(10) + 5;
            log.info("Aguardando {} segundos (Jittering) para evitar detecção mecânica.");

            try {
                TimeUnit.SECONDS.sleep(jitterSeconds);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Thread Interrompida durante o Jittering.");
            }

            engineService.processUrl(url);
        }

        log.info("Rotina de raspagem finalizada.");
    }
}
