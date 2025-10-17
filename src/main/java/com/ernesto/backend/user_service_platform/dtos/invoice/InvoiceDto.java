package com.ernesto.backend.user_service_platform.dtos.invoice;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ernesto.backend.user_service_platform.entities.enums.InvoiceStatus;

public record InvoiceDto(
    Long id,
    BigDecimal amount,
    LocalDateTime issuedAt,
    InvoiceStatus status,
    Long contractId,
    String userFullName,
    String serviceName
) {}
