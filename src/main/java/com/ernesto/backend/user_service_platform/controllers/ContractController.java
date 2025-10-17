package com.ernesto.backend.user_service_platform.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ernesto.backend.user_service_platform.dtos.contract.ContractDto;
import com.ernesto.backend.user_service_platform.dtos.contract.CreateContractDto;
import com.ernesto.backend.user_service_platform.dtos.contract.UpdateContractStatusDto;
import com.ernesto.backend.user_service_platform.entities.Contract;
import com.ernesto.backend.user_service_platform.mapper.ContractMapper;
import com.ernesto.backend.user_service_platform.services.ContractService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/contracts")
@Validated
public class ContractController {

    @Autowired
    private ContractService contractService;

    @Autowired 
    private ContractMapper contractMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> create(@Valid @RequestBody CreateContractDto createContractDto) {
        Contract contract = contractService.save(createContractDto);
        ContractDto response = contractMapper.toDto(contract);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<ContractDto> findById(@PathVariable @NotNull @Min(value = 1) Long id) {
        Contract contract = contractService.findById(id);
        ContractDto response = contractMapper.toDto(contract);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<ContractDto>> findByUserId(@PathVariable @NotNull @Min(value = 1) Long userId) {
        List<Contract> contracts = contractService.findByUserId(userId);
        List<ContractDto> contractsResponse = contracts.stream().map(contractMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(contractsResponse);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(
        @PathVariable @NotNull @Min(value = 1) Long id,
        @RequestBody @Valid UpdateContractStatusDto status
        ) {
            Contract contract = contractService.update(id, status);
            return ResponseEntity.status(HttpStatus.CREATED).body(contractMapper.toDto(contract));
        }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> remove(@PathVariable @Min(value = 1) @NotNull Long id) {
        contractService.remove(id);
        return ResponseEntity.noContent().build();
    }





}
