package com.ernesto.backend.user_service_platform.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ernesto.backend.user_service_platform.entities.ServiceEntity;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long>{

    boolean existsByName(String name);

}
