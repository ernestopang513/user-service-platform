package com.ernesto.backend.user_service_platform.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ernesto.backend.user_service_platform.entities.User;

import io.micrometer.common.lang.NonNull;

public interface UserRepository extends JpaRepository<User, Long>{

    @SuppressWarnings("null")
    @Override
    @EntityGraph(attributePaths = {"userRoles", "userRoles.role"})
    @NonNull
    List<User> findAll();

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
    

}
