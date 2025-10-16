package com.ernesto.backend.user_service_platform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ernesto.backend.user_service_platform.dtos.service.CreateServiceDto;
import com.ernesto.backend.user_service_platform.dtos.service.UpdateServiceDto;
import com.ernesto.backend.user_service_platform.entities.ServiceEntity;
import com.ernesto.backend.user_service_platform.services.ServiceService;


@RestController
@RequestMapping("/api/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @GetMapping()
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(serviceService.findAll());
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        ServiceEntity service = serviceService.findById(id);
        return ResponseEntity.ok(service);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateServiceDto createServiceDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceService.save(createServiceDto));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody UpdateServiceDto updateSerciveDto, @PathVariable Long id) {

        ServiceEntity service = serviceService.update(updateSerciveDto, id);

        return ResponseEntity.status(HttpStatus.CREATED).body(service);


    }

}
