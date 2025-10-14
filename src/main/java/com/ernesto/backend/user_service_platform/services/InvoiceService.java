package com.ernesto.backend.user_service_platform.services;

import java.util.List;
import java.util.Optional;

import com.ernesto.backend.user_service_platform.entities.Invoice;

public interface InvoiceService {

    List<Invoice> findByUsernameAndStatus(String username);

    Optional<Invoice> save(Invoice invoice);

    Optional<Invoice> update(Invoice invoice, Long id);

    void remove(Long id);



}
