package com.ernesto.backend.user_service_platform.dtos.contract;

public class CreateContractDto {

    private Long userId;

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
