package com.systemicr2.dtfutbol.service; // Ajusta el paquete si lo necesitas

import com.systemicr2.dtfutbol.model.AppUser;
import com.systemicr2.dtfutbol.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityValidationService {

    private final AppUserRepository appUserRepository;

    // NIVEL 3 - CÓMO FUNCIONA: Leemos el nombre del JWT y buscamos su entidad completa
    public AppUser getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado en la base de datos"));
    }

    // NIVEL 4 - CÓMO SE IMPLEMENTA: Comparamos el equipo del usuario con el equipo objetivo
    public void validateTeamOwnership(Long targetTeamId) {
        AppUser currentUser = getAuthenticatedUser();

        if (currentUser.getTeam() == null) {
            throw new AccessDeniedException("El usuario autenticado no tiene ningún equipo asignado.");
        }

        if (!currentUser.getTeam().getId().equals(targetTeamId)) {
            // ¡Aquí salta el muro (403 Forbidden)!
            throw new AccessDeniedException("Blindaje T-60: No tienes permisos para gestionar recursos del equipo " + targetTeamId);
        }
    }
}