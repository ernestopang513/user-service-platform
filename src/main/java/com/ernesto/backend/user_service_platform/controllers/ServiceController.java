package com.ernesto.backend.user_service_platform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ernesto.backend.user_service_platform.dtos.service.CreateServiceDto;
import com.ernesto.backend.user_service_platform.dtos.service.UpdateServiceDto;
import com.ernesto.backend.user_service_platform.entities.ServiceEntity;
import com.ernesto.backend.user_service_platform.services.ServiceService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


@RestController
@RequestMapping("/api/services")
@Validated
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> findAll(
        @RequestParam(required = false) Boolean active
    ) {
        return ResponseEntity.ok(serviceService.findAll(active));
    }
    

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> findById(@PathVariable @NotNull @Min(value=1) Long id) {
        ServiceEntity service = serviceService.findById(id);
        return ResponseEntity.ok(service);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody @Valid CreateServiceDto createServiceDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceService.save(createServiceDto));
    }


    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody @Valid UpdateServiceDto updateSerciveDto, @PathVariable Long id) {

        ServiceEntity service = serviceService.update(updateSerciveDto, id);

        return ResponseEntity.status(HttpStatus.CREATED).body(service);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> remove(@PathVariable @NotNull @Min(value=1) Long id) {
        serviceService.remove(id);
        return ResponseEntity.noContent().build(); 
    }

    @PatchMapping("/{id}/desactivate")
    @PreAuthorize("hasROle('ADMIN')")
    public ResponseEntity<?> desactivate(
        @PathVariable @NotNull @Min(value=1) Long id,
        @RequestParam(required = false) boolean active
        ) {
        serviceService.deactivate(id, active);
        return ResponseEntity.noContent().build();
    }
    
    

}
