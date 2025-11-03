package com.ernesto.backend.user_service_platform.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ernesto.backend.user_service_platform.dtos.contract.CreateContractDto;
import com.ernesto.backend.user_service_platform.dtos.contract.UpdateContractStatusDto;
import com.ernesto.backend.user_service_platform.entities.Contract;
import com.ernesto.backend.user_service_platform.entities.ServiceEntity;
import com.ernesto.backend.user_service_platform.entities.User;
import com.ernesto.backend.user_service_platform.entities.enums.ContractStatus;
import com.ernesto.backend.user_service_platform.exceptions.NotFoundException;
import com.ernesto.backend.user_service_platform.repositories.ContractRepository;
import com.ernesto.backend.user_service_platform.repositories.ServiceRepository;
import com.ernesto.backend.user_service_platform.repositories.UserRepository;

@Service
public class ContractServiceImp implements ContractService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ServiceRepository serviceRepository;


    @Override
    public Contract save(CreateContractDto createContractDto) {
        User user = userRepository.findById(createContractDto.getUserId())
            .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        ServiceEntity service = serviceRepository.findById(createContractDto.getServiceId())
            .orElseThrow(() -> new NotFoundException("Servicio no encontrado"));

        Contract contract = new Contract();
        contract.setUser(user);
        contract.setService(service);
        
        user.addContract(contract);
        return contractRepository.save(contract);
    }

   
    
    @Override
    public List<Contract> findByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        
        return contractRepository.findByUserIdAndStatus(user.getId(), ContractStatus.ACTIVE);
    }
    
    @Override
    public Contract findById(Long id) {
        return contractRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Contrato no encontrado"));
    }

    @Override
    public Contract update(Long id, UpdateContractStatusDto status) {

        if( !contractRepository.existsById(id)) {
            throw new NotFoundException("Contrato no encontrado");
        }

        Contract contract = findById(id);

        contract.setStatus(status.getStatus());

        return contractRepository.save(contract);

    }
    
    @Override
    public void remove(Long id) {
        findById(id);
        contractRepository.deleteById(id);
    }


}
