package com.ernesto.backend.user_service_platform.services;

import java.util.List;
import java.util.Optional;

import com.ernesto.backend.user_service_platform.entities.ServiceEntity;

public interface ServiceService {

    List<ServiceEntity> findAll();

    Optional<ServiceEntity> save(ServiceEntity service);

    Optional<ServiceEntity> update(ServiceEntity service, Long id);

    void remove(Long id);

}
