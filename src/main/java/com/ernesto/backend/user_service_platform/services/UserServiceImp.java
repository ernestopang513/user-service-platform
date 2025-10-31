package com.ernesto.backend.user_service_platform.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ernesto.backend.user_service_platform.dtos.user.CreateUserDto;
import com.ernesto.backend.user_service_platform.entities.Role;
import com.ernesto.backend.user_service_platform.entities.User;
import com.ernesto.backend.user_service_platform.entities.UserRole;
import com.ernesto.backend.user_service_platform.exceptions.BadRequestException;
import com.ernesto.backend.user_service_platform.exceptions.NotFoundException;
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
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User save(CreateUserDto createUserDto) {

        if (existsByUsername(createUserDto.getUsername()) ||  userRepository.existsByEmail(createUserDto.getEmail())) {
            throw new BadRequestException("El nombre de usuario o correo ya estan en uso");
        }

        Role roleUser = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new NotFoundException("Role no encontrado"));
        List<Role> roles = new ArrayList<>();

        roles.add(roleUser);

        User user = new User();

        user.setUsername(createUserDto.getUsername());
        user.setEmail(createUserDto.getEmail());
        user.setFull_name(createUserDto.getFull_name());
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        roles.forEach(user::addRole);

        return userRepository.save(user);
    }

    @Transactional
    public User saveAdmin(CreateUserDto createUserDto) {

        if (existsByUsername(createUserDto.getUsername()) ||  userRepository.existsByEmail(createUserDto.getEmail())) {
            throw new BadRequestException("El nombre de usuario o correo ya estan en uso");
        }

        Role roleAdmin = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new NotFoundException("Role no encontrado"));
        List<Role> roles = new ArrayList<>();
        roles.add(roleAdmin);

        User user = new User();
        user.setUsername(createUserDto.getUsername());
        user.setEmail(createUserDto.getEmail());
        user.setFull_name(createUserDto.getFull_name());
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        roles.forEach(itRole -> user.addRole(itRole));

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
    }

    @Override
    @Transactional
    public User update(CreateUserDto createUserDto, Long id) {

        if (existsByUsername(createUserDto.getUsername())||  userRepository.existsByEmail(createUserDto.getEmail())) {
            throw new BadRequestException("El nombre de usuario o correo ya está en uso");
        }

        User user = findById(id);

        user.setUsername(createUserDto.getUsername());
        user.setEmail(createUserDto.getEmail());
        user.setFull_name(createUserDto.getFull_name());

        return userRepository.save(user);

    }

    @Override
    @Transactional
    public void remove(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }

}
