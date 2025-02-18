package com.techhunt.deckster.game.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "game_card")
@NoArgsConstructor
@AllArgsConstructor
public class GameCard implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    private UUID gameId;
    private UUID cardId;

    public GameCard(UUID gameId, UUID cardId) {
        this.gameId = gameId;
        this.cardId = cardId;
    }
}
