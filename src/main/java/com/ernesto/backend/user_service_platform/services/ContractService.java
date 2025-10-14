package com.ernesto.backend.user_service_platform.services;

import java.util.List;
import java.util.Optional;

import com.ernesto.backend.user_service_platform.entities.Contract;

public interface ContractService {

    List<Contract> findByUserId(Long userId);

    Optional<Contract> save(Contract contract);

    Optional<Contract> update(Contract contract, Long id);

    void remove(Long id);

}
