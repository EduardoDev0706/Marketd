package com.example.marketd.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.marketd.domain.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    // Método Importante. Ao fazer a raspagem do site, verifica se a URL existe no banco.
    // Caso SIM = Atualiza o preço. Caso NÃO = Cria uma nova linha na tabela.
    Optional<Vehicle> findByUrl(String url);
}
