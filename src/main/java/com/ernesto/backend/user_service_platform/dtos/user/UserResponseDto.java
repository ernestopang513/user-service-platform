package com.ernesto.backend.user_service_platform.dtos.user;

import java.util.List;


public class UserResponseDto {

    private Long id;
    private String username;
    private String email;
    private boolean active;

    private String full_name;

    private List<String> roles;

    public UserResponseDto() {
    }
    
    public UserResponseDto(Long id, String username, boolean active, String full_name, String email, List<String> roles) {
        this.id = id;
        this.username = username;
        this.active = active;
        this.full_name = full_name;
        this.email = email;
        this.roles = roles;
        
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

  
    

}
