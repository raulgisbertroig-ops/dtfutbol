package com.systemicr2.dtfutbol.model;

// public: Modificador de acceso para que la entidad Subscription pueda leer estas direcciones de memoria.
// enum: Instrucción al compilador para crear una lista cerrada de constantes (Singletons) alojadas en la memoria estática.
public enum SubscriptionStatus {

    // PENDING: Estado inicial en RAM. El usuario ha iniciado el pago, pero la pasarela de red aún no ha confirmado los fondos.
    PENDING,

    // ACTIVE: El procesador ha validado el pago. El token de acceso a la plataforma está energizado y operativo.
    ACTIVE,

    // PAST_DUE: El ciclo de cobro ha fallado (ej. tarjeta rechazada). El sistema restringe recursos de CPU/Red para este cliente.
    PAST_DUE,

    // CANCELED: Terminación del contrato. El registro se mantiene en disco para auditoría, pero los punteros de acceso se destruyen.
    CANCELED
}