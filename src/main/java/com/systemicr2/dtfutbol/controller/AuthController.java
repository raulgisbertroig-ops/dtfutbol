package com.systemicr2.dtfutbol.controller;

import com.systemicr2.dtfutbol.dto.AuthRequestDTO;
import com.systemicr2.dtfutbol.dto.AuthResponseDTO;
import com.systemicr2.dtfutbol.model.AppUser;
import com.systemicr2.dtfutbol.repository.AppUserRepository;
import com.systemicr2.dtfutbol.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository userRepository; // Ojo: usa el nombre exacto de tu interfaz de repositorio aquí

    // Inyectamos todas las herramientas en el constructor
    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          PasswordEncoder passwordEncoder,
                          AppUserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
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
        String token = jwtUtil.generateToken(request.getUsername());

        // 3. RESPUESTA HTTP 200 OK con el Token dentro de la "caja" AuthResnponseDTO
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequestDTO request) {
        AppUser newUser = new AppUser();
        newUser.setUsername(request.getUsername());
        // VITAL: Encrypt the password before saving it to the database
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(newUser);
        return ResponseEntity.ok("User registered successfully");
    }
}
