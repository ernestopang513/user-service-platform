package com.ernesto.backend.user_service_platform.dtos.invoice;

import com.ernesto.backend.user_service_platform.entities.enums.InvoiceStatus;

import jakarta.validation.constraints.NotNull;

public class UpdateInvoiceStatusDto {

    @NotNull
    private InvoiceStatus status;

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    
}
