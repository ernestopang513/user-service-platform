package com.ernesto.backend.user_service_platform.listeners;

import org.springframework.beans.factory.annotation.Autowired;

import com.ernesto.backend.user_service_platform.entities.Contract;
import com.ernesto.backend.user_service_platform.services.AuditService;

import jakarta.persistence.PreRemove;

public class ContractListener {
    @Autowired
    private AuditService auditService;

    @PreRemove
    public void onPreRemove(Contract contract) {
        auditService.logDelete("Contract", contract.getId());
    }
}
