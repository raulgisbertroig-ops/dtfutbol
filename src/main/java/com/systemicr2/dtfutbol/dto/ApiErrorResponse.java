package com.systemicr2.dtfutbol.dto;

import java.time.LocalDateTime;

public class ApiErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;

    public ApiErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    // Generar los Getters a mano o añadir @Getter de Lombok
    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
