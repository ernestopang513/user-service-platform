package com.ernesto.backend.user_service_platform.mapper;

import org.springframework.stereotype.Component;

import com.ernesto.backend.user_service_platform.dtos.user.UserResponseDto;
import com.ernesto.backend.user_service_platform.entities.User;

@Component
public class UserMapper {
    public UserResponseDto toDto(User user) {
        return new UserResponseDto(
            user.getId(),
            user.getUsername(),
            user.isActive(),
            user.getFull_name(),
            user.getEmail(),
            // user.getRoles().stream().map(Role::getName).toList()
            user.getUserRoles().stream().map(userRole -> userRole.getRole().getName()).toList()
        );
    }
}
