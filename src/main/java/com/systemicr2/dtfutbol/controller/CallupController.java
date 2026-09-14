package com.systemicr2.dtfutbol.controller;

import com.systemicr2.dtfutbol.model.Callup;
import com.systemicr2.dtfutbol.service.CallupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/callups")
@RequiredArgsConstructor
public class CallupController {

    private final CallupService callupService;

    // REQUERIMIENTO 1: Crea un método público que devuelva un ResponseEntity<Callup> llamado 'addCallup'.
    // Debe responder a peticiones POST usando la anotación @PostMapping.
    @PostMapping

    // REQUERIMIENTO 2: El método debe recibir 4 parámetros usando @RequestParam.
    // (Long matchId, String playerId, boolean isStarter, int minutesPlayed).
    public ResponseEntity<Callup> addCallup(
            @RequestParam Long matchId,
            @RequestParam String playerId,
            @RequestParam boolean isStarter,
            @RequestParam int minutesPlayed) {


        // REQUERIMIENTO 3: Dentro del método, llama a callupService.addPlayerToMatch(...)
        // pasándole los 4 parámetros y guarda el resultado en una variable Callup.
        // La lógica la haremos en el siguiente paso
        // 3.1. Delegar een el servicio
        Callup newCallup = callupService.addPlayerToMatch(matchId, playerId, isStarter, minutesPlayed);

        // REQUERIMIENTO 4: Retorna un nuevo ResponseEntity que contenga esa variable Callup
        // y un estado HTTP de "CREADO" (HttpStatus.CREATED).
        // 4.1. Empaquetar y responder
        return new ResponseEntity<>(newCallup, HttpStatus.CREATED);
    }
}






