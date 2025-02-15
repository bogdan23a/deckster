package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.Game;

import java.util.List;
import java.util.UUID;

public interface GameService {

    List<Game> findAll();
    Game newGame(String email);
    void sendEvent(String event, String gameId);
    Game findById(UUID gameId);
}
