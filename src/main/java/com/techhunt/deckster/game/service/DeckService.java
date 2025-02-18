package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.Deck;
import com.techhunt.deckster.game.entity.Player;

import java.util.List;
import java.util.UUID;

public interface DeckService {
    
    List<Deck> findAll();
    Deck findById(UUID id);
    Deck save(Deck deck);
    Deck save(String filePath);
    void delete(UUID deckId);
    List<String> findAllNames();
    void dealHand(Player player, Deck deck);
}
