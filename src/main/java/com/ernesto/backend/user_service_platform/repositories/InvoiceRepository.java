package com.ernesto.backend.user_service_platform.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ernesto.backend.user_service_platform.entities.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>  {

    List<Invoice> findByUsernameAndStatus(String username);

    

}
