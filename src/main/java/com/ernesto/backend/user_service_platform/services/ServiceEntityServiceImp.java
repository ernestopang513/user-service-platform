package com.ernesto.backend.user_service_platform.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ernesto.backend.user_service_platform.dtos.service.CreateServiceDto;
import com.ernesto.backend.user_service_platform.dtos.service.UpdateServiceDto;
import com.ernesto.backend.user_service_platform.entities.ServiceEntity;
import com.ernesto.backend.user_service_platform.exceptions.BadRequestException;
import com.ernesto.backend.user_service_platform.exceptions.NotFoundException;
import com.ernesto.backend.user_service_platform.repositories.ServiceRepository;

@Service
@Transactional(readOnly = true)
public class ServiceEntityServiceImp implements ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public List<ServiceEntity> findAll(Boolean active) {
        if(active == null){
            return serviceRepository.findAll();
        }else {
            return serviceRepository.findByActive(active);
        }

    }

    @Override
    @Transactional(readOnly = false)
    public ServiceEntity save(CreateServiceDto createServiceDto) {
        if (serviceRepository.existsByName(createServiceDto.getName())) {
            throw new BadRequestException("El nombre del servicio ya está en uso");
        }

        ServiceEntity service = new ServiceEntity(
                createServiceDto.getName(),
                createServiceDto.getPrice());

        return serviceRepository.save(service);
    }

    @Override
    public ServiceEntity findById(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Servicio no encontrado"));
    }

    @Override
    @Transactional(readOnly = false)
    public ServiceEntity update(UpdateServiceDto updateServiceDto, Long id) {

        Optional<ServiceEntity> existingService = serviceRepository.findByName(updateServiceDto.getName());
        if (serviceRepository.existsByName(updateServiceDto.getName()) && !existingService.get().getId().equals(id)) {
            throw new BadRequestException("El nombre del servicio ya esta en uso");
        }

        ServiceEntity service = findById(id);

        service.setName(updateServiceDto.getName());
        service.setPrice(updateServiceDto.getPrice());

        return serviceRepository.save(service);
    }

    @Override
    @Transactional(readOnly = false)
    public void remove(Long id) {
        findById(id);
        serviceRepository.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void deactivate(Long id, boolean active) {
        ServiceEntity service =findById(id);
        service.setActive(active);
        serviceRepository.save(service);
    }

}
