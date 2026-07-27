package com.example.marketd.service;

import java.time.LocalDateTime;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.marketd.domain.Vehicle;
import com.example.marketd.event.VehicleScrapedEvent;
import com.example.marketd.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class VehicleStorageListener {

    private final VehicleRepository repository;

    // O método é automaticamente quando o evento for disparado
    @EventListener
    public void onVehicleScraped(VehicleScrapedEvent event) {
        Vehicle scrapedData = event.vehicle();

        repository.findByUrl(scrapedData.getUrl()).ifPresentOrElse(
                existingVehicle -> {
                    log.info("Veículo já existe no banco. Atualizando preço...");
                    existingVehicle.setPrice(scrapedData.getPrice());
                    existingVehicle.setScraped_at(LocalDateTime.now());
                    repository.save(existingVehicle);
                },
                () -> {
                    log.info("Novo veículo detectado! Inserindo no banco de dados...");
                    repository.save(scrapedData);
                });
    }
}
