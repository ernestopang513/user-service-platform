package com.ernesto.backend.user_service_platform.security;

import static com.ernesto.backend.user_service_platform.security.TokenJwtConfig.SECRET_KEY;

import java.util.Date;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.ernesto.backend.user_service_platform.entities.Role;
import com.ernesto.backend.user_service_platform.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtService {

    public String generateToken(Authentication authentication, User user) {
        try {
            // String username = authentication.getName();
            var roles = authentication.getAuthorities();

            Map<String, Object> userData = Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "email", user.getEmail(),
                "full_name", user.getFull_name(),
                "roles", user.getRoles().stream().map(Role::getName).toList()
            );


            Claims claims = Jwts.claims()
                .add("authorities", new ObjectMapper().writeValueAsString(roles))
                .add("user", userData)
                .build();

            return Jwts.builder()
                .subject(user.getUsername())
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .issuedAt(new Date())
                .signWith(SECRET_KEY)
                .compact();

        } catch (Exception e) {
            throw new RuntimeException("Error generando token");
        }
    }
}
