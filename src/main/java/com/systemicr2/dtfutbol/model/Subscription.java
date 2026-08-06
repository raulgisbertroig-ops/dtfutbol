package com.systemicr2.dtfutbol.model;

import jakarta.persistence.*;
import java.time.Instant;

// @Entity: Directiva para mapear esta estructura de la RAM al clúster físico de MySQL.
@Entity
// @Table: Enrutamiento que conecta esta clase con la tabla física "subscriptions".
@Table(name = "subscriptions")
// public: Expone el bloque al subsistema ClassLoader.
// class: Plantilla de asignación de memoria en el Heap de la JVM.
public class Subscription {

    // @Id: Define el puntero primario en el índice de la base de datos.
    @Id
    // @GeneratedValue: Delega el autoincremento al procesador de MySQL para ahorrar ciclos de CPU en el backend.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private: Restricción de acceso estricto a esta dirección de memoria.
    private Long id;

    // @Column: Instrucción DDL que prohíbe inserciones nulas a nivel de motor de almacenamiento.
    @Column(name = "customer_id", nullable = false)
    // String: Matriz de caracteres en memoria que retendrá el token del proveedor de pagos.
    private String customerId;

    // @Enumerated: Transforma la lectura estática de la memoria (Enum) a un registro de texto plano en disco.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatus status;

    // @Version: Semáforo de hardware. Evita que dos hilos concurrentes corrompan la memoria al escribir al mismo tiempo.
    @Version
    @Column(nullable = false)
    private Long version;

    // Instant: Marca de nanosegundos obtenida directamente del reloj del sistema operativo (RTC).
    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    // Getters / Setters: Canales de redirección para leer/escribir las variables encapsuladas.
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public SubscriptionStatus getStatus() { return status; }
    public void setStatus(SubscriptionStatus status) { this.status = status; }

    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }
}
