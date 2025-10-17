package com.ernesto.backend.user_service_platform.services;

import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuditService {
    public void logDelete(String entity, Long id) {
        String username = getCurrentUsername();
        System.out.printf("🧾 [%s] El usuario '%s' eliminó la entidad %s con ID %d%n",
                LocalDateTime.now(), username, entity, id);
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return "anonymous";
    }
}
