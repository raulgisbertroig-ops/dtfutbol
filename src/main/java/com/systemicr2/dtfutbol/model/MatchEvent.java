package com.systemicr2.dtfutbol.model;

import com.systemicr2.dtfutbol.model.enums.EventType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "match_events")
@Data
@NoArgsConstructor
public class MatchEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ¿DÓNDE ocurrrió?
    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    // ¿QUIÉNlo hizo?
    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    // ¿QUÉ ocurrió? (Lista de opciones estadísticas)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventType eventType;

    // ¿CUÁNDO ocurrió?
    @Column(nullable = false)
    private Integer matchMinute;

    // DETALLES (Texto Libre para el acta)
    private String notes;
}

