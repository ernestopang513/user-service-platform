package com.ernesto.backend.user_service_platform.dtos.contract;

import com.ernesto.backend.user_service_platform.entities.enums.ContractStatus;

import jakarta.validation.constraints.NotNull;

public class UpdateContractStatusDto {

    @NotNull
    private ContractStatus status;

    public ContractStatus getStatus() {
        return status;
    }

    public void setStatus(ContractStatus status) {
        this.status = status;
    }
    
}
