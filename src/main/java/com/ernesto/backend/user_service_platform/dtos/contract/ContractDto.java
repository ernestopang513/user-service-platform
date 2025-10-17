package com.ernesto.backend.user_service_platform.dtos.contract;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ernesto.backend.user_service_platform.entities.enums.ContractStatus;

public record ContractDto(
    Long id,
    String serviceName,
    BigDecimal servicePrice,
    LocalDateTime startDate,
    LocalDateTime endDate,
    ContractStatus status,
    String userFullName,
    String userEmail
) 
{}

