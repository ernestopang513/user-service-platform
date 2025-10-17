package com.ernesto.backend.user_service_platform.services;

import java.util.List;

import com.ernesto.backend.user_service_platform.dtos.contract.CreateContractDto;
import com.ernesto.backend.user_service_platform.dtos.contract.UpdateContractStatusDto;
import com.ernesto.backend.user_service_platform.entities.Contract;

public interface ContractService {

    List<Contract> findByUserId(Long userId);

    Contract save(CreateContractDto createContractDto);

    Contract findById(Long id);

    Contract update(Long id, UpdateContractStatusDto status);


    void remove(Long id);

}
