package com.ernesto.backend.user_service_platform.controllers;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ernesto.backend.user_service_platform.entities.Role;
import com.ernesto.backend.user_service_platform.entities.User;
import com.ernesto.backend.user_service_platform.repositories.UserRepository;
import com.ernesto.backend.user_service_platform.security.JwtService;


@RestController
@RequestMapping("/check-status")
public class CheckStatusController {

    private final JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    public CheckStatusController(JwtService jwtService) {
        this.jwtService = jwtService;
    }



    @GetMapping
    public ResponseEntity<?> checkStatus(Authentication authentication) {

        if(authentication == null || !authentication.isAuthenticated() ) {
            return ResponseEntity.status(401).body(Map.of("error", "Usuario no autenticado"));
        } 

        
        User authUser = (User) authentication.getPrincipal();
        
        User user = userRepository.findById(authUser.getId())
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        // String username = authentication.getName();
        
        String newToken = jwtService.generateToken(authentication, user);
        // var principal = authentication.getPrincipal();
        // System.out.println(principal.toString() + ".getPrincipal&&&&");
        // var roles = authentication.getAuthorities();
        // System.out.println(roles);

        //  com.ernesto.backend.examen.backend_examen.models.entities.User user =
        //     (com.ernesto.backend.examen.backend_examen.models.entities.User) authentication.getPrincipal();
        // System.out.println(user);

        Map<String, Object> userData = Map.of(
            "id", user.getId(),
            "username", user.getUsername(),
            // "email", user.getEmail(),
            "roles", user.getRoles().stream().map(Role::getName).toList()
        );


        return ResponseEntity.ok(Map.of(
            // "message", "Token renovado correctamente",
            "token", newToken,
            "user", userData
        ));

        // return ResponseEntity.ok().build();
    }
}
