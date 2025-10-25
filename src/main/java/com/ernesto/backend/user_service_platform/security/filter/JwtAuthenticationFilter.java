package com.ernesto.backend.user_service_platform.security.filter;

import static com.ernesto.backend.user_service_platform.security.TokenJwtConfig.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ernesto.backend.user_service_platform.entities.Role;
import com.ernesto.backend.user_service_platform.entities.User;
import com.ernesto.backend.user_service_platform.repositories.UserRepository;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    private AuthenticationManager authenticationManager;
    private final UserRepository userRepository;




    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        setFilterProcessesUrl("/api/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
            
        User user = null;
        String username = null;
        String password = null;

        try {
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            username = user.getUsername();
            password = user.getPassword();
        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

                org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
                String username = user.getUsername();

                User appUser = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado en base de datos")  );


                Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

                Map<String, Object> userData = Map.of(
                    "id", appUser.getId(),
                    "username", appUser.getUsername(),
                    "email", appUser.getEmail(),
                    "full_name", appUser.getFull_name(),
                    "roles", roles.stream().map(role -> role.getAuthority()).toList()
                );


                Claims claims = Jwts.claims()
                    .add("authorities", new ObjectMapper().writeValueAsString(roles) )
                    .add("user", userData)
                    // .add("username", username)
                .build();
                

                String token = Jwts.builder()
                    .subject(appUser.getUsername())
                    .claims(claims)
                    .expiration(new Date(System.currentTimeMillis()+ 3600000))
                    // .expiration(new Date(System.currentTimeMillis()+ 60000))
                    .issuedAt(new Date())
                    .signWith(SECRET_KEY)
                    .compact();

                response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);

                Map<String, Object> body = new HashMap<>();
                body.put("token",token);
                body.put("user", userData);
                // body.put("username", username);
                // body.put("message", String.format("Hola %s has iniciado sesion con exito.", username));

                response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                response.setContentType(CONTENT_TYPE);
                response.setStatus(200);
            
            }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
                Map<String, String> body = new HashMap<>();
                body.put("message", "Error en la autentificación username o password incorrectos.");
                body.put("error", failed.getMessage());

                response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                response.setStatus(401);
                response.setContentType(CONTENT_TYPE);
    }

            

    


}