package com.systemicr2.dtfutbol.util;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // 1. Clave secreta (Debe ser muy larga para el algoritmo HS256)
    // En el futuro , esto no se pondra en el cçodigo, sino en variables de entorno ocultas.
    private final String SECRET_KEY = "dtfutbol_secreta_super_segura_para_dam_2026_minimo_32_caracteres";

    // 2. Tiempo de expiración (Ejemplo: 10 horas en milisegundos)
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;

    // 3. Método auxiliar: Prepara la llave criptográfica
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // 4. Motor principal: Fabrica la pulsera VIP
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // PAYLOAD: Guardamos quién es el entrenador
                .setIssuedAt(new Date(System.currentTimeMillis())) // Fecha de creación (ahora)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Fecha de caducidad
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // SIGNATURE: Firmamos con nuestra clave y el algoritmo
                .compact(); // Construye el String final con los famosos tres bloques separados por puntos
    }

    // 5. Lector: Extrae el nombre de usuario de dentro de la pulsera VIP
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // Usamos la misma llave secreta para abrirlo
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // Recupera el "username· que guardamos en el Payload
    }
}
