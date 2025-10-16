package com.ernesto.backend.user_service_platform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ernesto.backend.user_service_platform.entities.enums.InvoiceStatus;
import com.ernesto.backend.user_service_platform.services.InvoiceService;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/invoices")
@Validated
public class InvoiceController {


    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/{contractId}/all")
    public ResponseEntity<?> findByContractAndStatus(
        @PathVariable @NotNull @Min(value=1) Long contractId,
        @RequestParam(required = false) InvoiceStatus status
        ) {
        return ResponseEntity.ok(invoiceService.findByContract_IdAndStatus(contractId, status ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable @NotNull @Min(value=1) Long id) {
        return ResponseEntity.ok(invoiceService.findById(id)) ;

    }

    @PostMapping("/api/contracts/{contractId}/invoices")
    public ResponseEntity<?> create(@PathVariable @NotNull @Min(value=1) Long contractId) {   
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceService.save(contractId));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable @NotNull @Min(value=1) Long id) {
        invoiceService.remove(id);
        return ResponseEntity.noContent().build();
    }

}
