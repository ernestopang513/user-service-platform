package com.ernesto.backend.user_service_platform.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ernesto.backend.user_service_platform.entities.Invoice;
import com.ernesto.backend.user_service_platform.entities.enums.InvoiceStatus;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>  {

    List<Invoice> findByContract_Id(Long contractId);
    
    List<Invoice> findByContract_IdAndStatus(Long contractId, InvoiceStatus status);
    

}
