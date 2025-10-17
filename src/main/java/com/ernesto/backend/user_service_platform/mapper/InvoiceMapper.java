package com.ernesto.backend.user_service_platform.mapper;

import org.springframework.stereotype.Component;

import com.ernesto.backend.user_service_platform.dtos.invoice.InvoiceDto;
import com.ernesto.backend.user_service_platform.entities.Invoice;

@Component
public class InvoiceMapper {
    public InvoiceDto toDto(Invoice invoice){
        return new InvoiceDto(
            invoice.getId(),
            invoice.getAmount(),
            invoice.getIssued_at(),
            invoice.getStatus(),
            invoice.getContract().getId(),
            invoice.getContract().getUser().getFull_name(),
            invoice.getContract().getService().getName()
        );
    }
}
