package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.GameTask;
import com.techhunt.deckster.game.enums.GameState;

import java.util.UUID;

public interface TaskService {
    GameTask getTask(UUID gameId, GameState gameState);
}
