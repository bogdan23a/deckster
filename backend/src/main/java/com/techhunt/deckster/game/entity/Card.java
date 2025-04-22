package com.techhunt.deckster.game.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "card")
@NoArgsConstructor
@AllArgsConstructor
public class Card implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    private String content;

    @Column(name = "deck_id")
    private UUID deckId;

    @ManyToOne
    @JoinColumn(name ="type_id")
    private CardType type;

    @Column(name = "player_email")
    private String playerEmail;

    private int prompts;

    public Card(String content, UUID deckId, CardType type) {
        this.content = content;
        this.deckId = deckId;
        this.type = type;
    }

    public Card(String content, CardType type) {
        this.content = content;
        this.type = type;
    }

    public Card(String content, CardType type, int prompts) {
        this.content = content;
        this.type = type;
        this.prompts = prompts;
    }
}