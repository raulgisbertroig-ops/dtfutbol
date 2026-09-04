package com.systemicr2.dtfutbol.controller;

import com.systemicr2.dtfutbol.dto.AuthRequestDTO;
import com.systemicr2.dtfutbol.dto.AuthResponseDTO;
import com.systemicr2.dtfutbol.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // Aqui dentro inyectamos el AuthenticationManager y el JwUtil
    // para procesar el login en el siguiente paso.

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwUtil;

    // Inyectamos por constructor las dos herramientas clave
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwUtil) {
        this.authenticationManager = authenticationManager;
        this.jwUtil = jwUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {

        // 1. EL PORTTERO COMPRUEBA LAS CREDENCIALES
        // Si el usuario o la contraseña no coinciden con la base de datos,
        // Spring lanzará una excepción automáticamente y denegara el acceso (401).
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // 2. FABRICACIÓN DEL TOKEN
        // Si el paso anterior tuvo éxito, generamos el token JWT con su nombre de usuario.
        String token = jwUtil.generateToken(request.getUsername());

        // 3. RESPUESTA HTTP 200 OK con el Token dentro de la "caja" AuthResnponseDTO
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}
