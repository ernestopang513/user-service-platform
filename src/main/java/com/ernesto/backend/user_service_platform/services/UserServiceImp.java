package com.ernesto.backend.user_service_platform.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ernesto.backend.user_service_platform.dtos.user.CreateUserDto;
import com.ernesto.backend.user_service_platform.dtos.user.UserResponseDto;
import com.ernesto.backend.user_service_platform.entities.Role;
import com.ernesto.backend.user_service_platform.entities.User;
import com.ernesto.backend.user_service_platform.repositories.RoleRepository;
import com.ernesto.backend.user_service_platform.repositories.UserRepository;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly= true)
    public List<UserResponseDto> findAll() {
        return userRepository.findAll()
            .stream()
            .map(user -> new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.isActive()
            ))
            .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public Optional<User> save(CreateUserDto createUserDto) {



        if( existsByUsername(createUserDto.getUsername() ) ) {
            return Optional.empty();
        }

        Optional<Role> optRoleUser = roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>(); 

        optRoleUser.ifPresent(roles::add);

        User user = new User();

        user.setUsername(createUserDto.getUsername());
        // user.setUsername(createUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        user.setRoles(roles);

        try {
            return Optional.of(userRepository.save(user));
        } catch (DataIntegrityViolationException e) {
            return Optional.empty();
        }

    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    
    @Override
    public Optional<User> update(CreateUserDto user, Long id) {
        Optional<User> o = findById(id);
        User userOptional = null;
        if (o.isPresent()) {
            User userDB = o.orElseThrow();
            userDB.setUsername(user.getUsername());
            userOptional = userRepository.save(userDB);
        }
        return Optional.ofNullable(userOptional);
    }
    
    
    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }
    
}
