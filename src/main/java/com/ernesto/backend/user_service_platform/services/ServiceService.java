package com.ernesto.backend.user_service_platform.services;

import java.util.List;
import java.util.Optional;

import com.ernesto.backend.user_service_platform.entities.Service;

public interface ServiceService {

    List<Service> findAll();

    Optional<Service> save(Service service);

    Optional<Service> update(Service service, Long id);

    void remove(Long id);

}
