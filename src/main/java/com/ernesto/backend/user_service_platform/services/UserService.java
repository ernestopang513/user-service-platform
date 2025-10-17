package com.ernesto.backend.user_service_platform.services;

import java.util.List;

import com.ernesto.backend.user_service_platform.dtos.user.CreateUserDto;
import com.ernesto.backend.user_service_platform.entities.User;

public interface UserService {

    List<User> findAll();

    User save(CreateUserDto user);

    boolean existsByUsername(String username);

    User findById(Long id);

    void remove(Long id);

    User update(CreateUserDto user, Long id);

    

    

}
