package com.ernesto.backend.user_service_platform.controllers;

import java.util.List;
import java.util.stream.Collectors;

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

import com.ernesto.backend.user_service_platform.dtos.invoice.InvoiceDto;
import com.ernesto.backend.user_service_platform.entities.Invoice;
import com.ernesto.backend.user_service_platform.entities.enums.InvoiceStatus;
import com.ernesto.backend.user_service_platform.mapper.InvoiceMapper;
import com.ernesto.backend.user_service_platform.services.InvoiceService;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/invoices")
@Validated
public class InvoiceController {


    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceMapper invoiceMapper;

    @GetMapping("/{contractId}/all")
    public ResponseEntity<List<InvoiceDto>> findByContractAndStatus(
        @PathVariable @NotNull @Min(value=1) Long contractId,
        @RequestParam(required = false) InvoiceStatus status
        ) {
            List<Invoice> invoices = invoiceService.findByContract_IdAndStatus(contractId, status );
            List<InvoiceDto> invoicesResponse = invoices.stream().map(invoiceMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(invoicesResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDto> findById(@PathVariable @NotNull @Min(value=1) Long id) {
        Invoice invoice = invoiceService.findById(id);
        InvoiceDto invoiceResonse = invoiceMapper.toDto(invoice);
        return ResponseEntity.ok(invoiceResonse);

    }

    @PostMapping("/contracts/{contractId}")
    public ResponseEntity<InvoiceDto> create(@PathVariable @NotNull @Min(value=1) Long contractId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceMapper.toDto(invoiceService.save(contractId)));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable @NotNull @Min(value=1) Long id) {
        invoiceService.remove(id);
        return ResponseEntity.noContent().build();
    }

    //Todo Metodo que simula el pago

}
