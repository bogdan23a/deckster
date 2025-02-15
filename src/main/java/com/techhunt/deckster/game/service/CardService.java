package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.Card;

import java.util.List;
import java.util.UUID;

public interface CardService {
    List<Card> findAll();
    void save(Card card);
    List<Card> findByDeckId(UUID deckId);
}
