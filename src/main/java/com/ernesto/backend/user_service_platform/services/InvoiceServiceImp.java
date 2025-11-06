package com.ernesto.backend.user_service_platform.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ernesto.backend.user_service_platform.dtos.invoice.UpdateInvoiceStatusDto;
import com.ernesto.backend.user_service_platform.entities.Contract;
import com.ernesto.backend.user_service_platform.entities.Invoice;
import com.ernesto.backend.user_service_platform.entities.enums.InvoiceStatus;
import com.ernesto.backend.user_service_platform.exceptions.NotFoundException;
import com.ernesto.backend.user_service_platform.repositories.ContractRepository;
import com.ernesto.backend.user_service_platform.repositories.InvoiceRepository;

@Service
public class InvoiceServiceImp implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Override
    public List<Invoice> findByContract_IdAndStatus(Long contractId, InvoiceStatus status) {

        contractRepository.findById(contractId).orElseThrow(() -> new NotFoundException("Contrato no encontrado"));

        if(status != null) {
            return invoiceRepository.findByContract_IdAndStatus(contractId, status);
        }

        return invoiceRepository.findByContract_Id(contractId);
    }

    @Override
    public Invoice save(Long contracId) {

        Contract contract = contractRepository.findById(contracId)
            .orElseThrow(() -> new NotFoundException("Contrato no entontrado"));

        BigDecimal amount = contract.getService().getPrice();

        
        Invoice invoice = new Invoice();

        invoice.setAmount(amount);
        
        contract.addInvoice(invoice);

        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice findById(Long id) {
        return invoiceRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Factura no encontrada"));
    }

    @Override
    public Invoice update(UpdateInvoiceStatusDto status, Long id) {
        Invoice invoiceDb = findById(id);
        invoiceDb.setStatus(status.getStatus());

        return invoiceRepository.save(invoiceDb);
    }

    @Override
    public Invoice payInvoice(Long id) {
        Invoice invoice = findById(id);
        invoice.setStatus(InvoiceStatus.PAID);
        return invoiceRepository.save(invoice);
    }

    @Override
    public void remove(Long id) {
        findById(id);

        invoiceRepository.deleteById(id);
    }

    

}
