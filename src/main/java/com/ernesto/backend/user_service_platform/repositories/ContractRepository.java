package com.ernesto.backend.user_service_platform.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ernesto.backend.user_service_platform.entities.Contract;
import com.ernesto.backend.user_service_platform.entities.enums.ContractStatus;

public interface ContractRepository extends JpaRepository<Contract, Long>{

    List<Contract> findByUserIdAndStatus(Long userId, ContractStatus status); 

}
