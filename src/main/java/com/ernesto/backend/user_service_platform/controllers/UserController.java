package com.ernesto.backend.user_service_platform.controllers;

import java.util.List;

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
        User user = userService.findById(id);
            return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateUserDto createUserDto) {
        User user = userService.save(createUserDto);

        //* Se puede poner un MapStruct o ModelMapper para no contruir manualmente user resp dto

            UserResponseDto newUser = new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.isActive()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CreateUserDto createUserDto) {
        return create(createUserDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody CreateUserDto createUserDto, @PathVariable Long id) {
        User user = userService.update(createUserDto, id);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
            userService.remove(id);
            return ResponseEntity.noContent().build();
    }

}
