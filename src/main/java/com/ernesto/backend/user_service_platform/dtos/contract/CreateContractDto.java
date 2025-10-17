package com.ernesto.backend.user_service_platform.dtos.contract;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CreateContractDto {

    @NotNull
    @Min(value=1, message = "Debe ser >= 1")
    private Long userId;

    @NotNull
    @Min(value=1, message = "Debe ser >= 1")
    private Long serviceId;

   
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    

}
