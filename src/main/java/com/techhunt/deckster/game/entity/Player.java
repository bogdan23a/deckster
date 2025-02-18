package com.techhunt.deckster.game.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "player")
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    @Id
    @Column(name = "email")
    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_email")
    private Set<GameCard> hand;

    @Column(name = "game_id")
    private UUID gameId;

    private boolean dealer;

    private int score;

    public Player(String email, UUID gameId, boolean dealer) {
        this.email = email;
        this.gameId = gameId;
        this.dealer = dealer;
    }
}
