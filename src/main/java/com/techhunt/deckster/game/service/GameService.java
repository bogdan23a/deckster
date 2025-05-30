package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.Card;
import com.techhunt.deckster.game.entity.Game;

import java.util.List;
import java.util.UUID;

public interface GameService {

    List<Game> findAll();
    Game newGame(String email);
    Game findById(UUID gameId);
    void save(Game game);
    void dealCards(UUID gameId);
    void choosePrompt(UUID gameId, String email);
    Card getPrompt(UUID gameId);
    void removeUsedCardsFromUsersHands(UUID gameId);
}
