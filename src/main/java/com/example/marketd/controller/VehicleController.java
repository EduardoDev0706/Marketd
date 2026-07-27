package com.example.marketd.controller;

import org.apache.catalina.connector.Response;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.marketd.domain.Vehicle;
import java.util.List;
import com.example.marketd.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleRepository repository;

    @GetMapping
    public ResponseEntity<List<Vehicle>> getBestDeals() {
        List<Vehicle> vehicles = repository.findAll(Sort.by(Sort.Direction.ASC, "price"));

        return ResponseEntity.ok(vehicles);
    }
}
