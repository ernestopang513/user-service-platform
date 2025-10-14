package com.ernesto.backend.user_service_platform.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ernesto.backend.user_service_platform.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String Name);

    
}
