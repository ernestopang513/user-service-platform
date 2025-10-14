package com.ernesto.backend.user_service_platform.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ernesto.backend.user_service_platform.entities.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long>{

    List<Contract> findByUserId(Long userId); 

}
