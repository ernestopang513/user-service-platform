package com.ernesto.backend.user_service_platform.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ernesto.backend.user_service_platform.dtos.contract.CreateContractDto;
import com.ernesto.backend.user_service_platform.entities.Contract;
import com.ernesto.backend.user_service_platform.services.ContractService;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateContractDto createContractDto) {
        Contract contract = contractService.save(createContractDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(contract);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Contract contract = contractService.findById(id);
        return ResponseEntity.ok(contract);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> findByUserId(@PathVariable Long userId) {
        List<Contract> contracts = contractService.findByUserId(userId);
        return ResponseEntity.ok(contracts);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        contractService.remove(id);
        return ResponseEntity.noContent().build();
    }





}
