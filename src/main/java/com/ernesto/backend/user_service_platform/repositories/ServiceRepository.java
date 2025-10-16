package com.ernesto.backend.user_service_platform.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ernesto.backend.user_service_platform.entities.ServiceEntity;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long>{

    boolean existsByName(String name);

    List<ServiceEntity> findByActive(boolean active);

}
