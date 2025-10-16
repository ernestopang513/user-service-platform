package com.ernesto.backend.user_service_platform.services;

import java.util.List;

import com.ernesto.backend.user_service_platform.dtos.contract.CreateContractDto;
import com.ernesto.backend.user_service_platform.entities.Contract;
import com.ernesto.backend.user_service_platform.entities.enums.ContractStatus;

public interface ContractService {

    List<Contract> findByUserId(Long userId);

    Contract save(CreateContractDto createContractDto);

    Contract findById(Long id);

    Contract update(Long id, ContractStatus contractStatus);

    void remove(Long id);

}
