package com.ernesto.backend.user_service_platform.services;

import java.util.List;
import java.util.Optional;

import com.ernesto.backend.user_service_platform.dtos.user.CreateUserDto;
import com.ernesto.backend.user_service_platform.dtos.user.UserResponseDto;
import com.ernesto.backend.user_service_platform.entities.User;

public interface UserService {

    List<UserResponseDto> findAll();

    Optional<User> save(CreateUserDto user);

    boolean existsByUsername(String username);

    Optional<User> findById(Long id);

    void remove(Long id);

    Optional<User> update(CreateUserDto user, Long id);

    

    

}
