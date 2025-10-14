package com.ernesto.backend.user_service_platform.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.ernesto.backend.user_service_platform.entities.User;
import com.ernesto.backend.user_service_platform.services.UserServiceImp;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImp userService;


    @GetMapping
    public List<UserResponseDto> list() {
        return userService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
        
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateUserDto createUserDto) {
        Optional<User> userOpt = userService.save(createUserDto);
        if(userOpt.isPresent()) {

            UserResponseDto user = new UserResponseDto(
                userOpt.get().getId(),
                userOpt.get().getUsername(),
                userOpt.get().isActive()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("El nombre de usuario ya esta en uso.");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CreateUserDto createUserDto) {
        return create(createUserDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody CreateUserDto user, @PathVariable Long id) {
        Optional<User> o = userService.update(user, id);
        if(o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        Optional<User> o = userService.findById(id);
        if(o.isPresent()) {
            userService.remove(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.noContent().build();
    }

}
