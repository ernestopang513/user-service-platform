package com.ernesto.backend.user_service_platform.services;

import java.util.List;

import com.ernesto.backend.user_service_platform.dtos.service.CreateServiceDto;
import com.ernesto.backend.user_service_platform.dtos.service.UpdateServiceDto;
import com.ernesto.backend.user_service_platform.entities.ServiceEntity;

public interface ServiceService {

    List<ServiceEntity> findAll(Boolean active);

    ServiceEntity save(CreateServiceDto createServiceDto);

    ServiceEntity findById(Long id);

    ServiceEntity update(UpdateServiceDto service, Long id);

    void remove(Long id);

    void deactivate(Long id, boolean active);

}
