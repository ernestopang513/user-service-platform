package com.ernesto.backend.user_service_platform.services;

import java.util.List;

import com.ernesto.backend.user_service_platform.dtos.invoice.UpdateInvoiceStatusDto;
import com.ernesto.backend.user_service_platform.entities.Invoice;
import com.ernesto.backend.user_service_platform.entities.enums.InvoiceStatus;

public interface InvoiceService {

    List<Invoice> findByContract_IdAndStatus(Long contractId, InvoiceStatus status);

    Invoice findById(Long id);

    Invoice save(Long contractId);

    Invoice update(UpdateInvoiceStatusDto status, Long id);

    Invoice payInvoice(Long id);

    void remove(Long id);



}
