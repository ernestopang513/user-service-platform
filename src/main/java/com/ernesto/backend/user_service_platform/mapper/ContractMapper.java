package com.ernesto.backend.user_service_platform.mapper;

import org.springframework.stereotype.Component;

import com.ernesto.backend.user_service_platform.dtos.contract.ContractDto;
import com.ernesto.backend.user_service_platform.entities.Contract;

@Component
public class ContractMapper {
    public ContractDto toDto(Contract contract) {
        return new ContractDto(
                contract.getId(),
                contract.getService().getName(),
                contract.getService().getPrice(),
                contract.getStart_date(),
                contract.getEnd_date(),
                contract.getStatus(),
                contract.getUser().getFull_name(),
                contract.getUser().getEmail());
    }
}
