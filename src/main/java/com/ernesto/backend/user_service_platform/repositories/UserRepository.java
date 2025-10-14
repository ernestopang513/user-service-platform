package com.ernesto.backend.user_service_platform.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ernesto.backend.user_service_platform.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
    

}
