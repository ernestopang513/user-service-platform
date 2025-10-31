package com.ernesto.backend.user_service_platform.controllers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ernesto.backend.user_service_platform.dtos.user.CreateUserDto;
import com.ernesto.backend.user_service_platform.dtos.user.UserResponseDto;
import com.ernesto.backend.user_service_platform.entities.Role;
import com.ernesto.backend.user_service_platform.entities.User;
import com.ernesto.backend.user_service_platform.mapper.UserMapper;
import com.ernesto.backend.user_service_platform.services.UserServiceImp;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    @Autowired
    private UserServiceImp userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(userService.findAll().stream().map(userMapper::toDto).collect(Collectors.toList()));
    }

    @PostMapping("/admins")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createAdmin(@Valid @RequestBody CreateUserDto createUserDto) {
        User admin = userService.saveAdmin(createUserDto);

        UserResponseDto newAdmin = new UserResponseDto(
                admin.getId(),
                admin.getUsername(),
                admin.isActive(),
                admin.getFull_name(),
                admin.getEmail(),
                // admin.getRoles().stream().map(Role::getName).toList()
                admin.getUserRoles().stream().map(userRole -> userRole.getRole().getName()).toList()
                );

        return ResponseEntity.status(HttpStatus.CREATED).body(newAdmin);
    }

    
    private ResponseEntity<?> create(@Valid @RequestBody CreateUserDto createUserDto) {
        User user = userService.save(createUserDto);

        // * Se puede poner un MapStruct o ModelMapper para no contruir manualmente user
        // resp dto

        UserResponseDto userResp = userMapper.toDto(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResp);
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody CreateUserDto createUserDto) {
        return create(createUserDto);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable @NotNull @Min(value=1) Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody @Valid CreateUserDto createUserDto, @PathVariable @NotNull @Min(value=1) Long id) {
        User user = userService.update(createUserDto, id);

        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDto(user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> remove(@PathVariable @NotNull @Min(value=1) Long id) {
        userService.remove(id);
        return ResponseEntity.noContent().build();
    }

}
