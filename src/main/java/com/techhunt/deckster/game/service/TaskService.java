package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.GameTask;
import com.techhunt.deckster.game.enums.GameState;

import java.util.Map;

public interface TaskService {
    GameTask getTask(GameState gameState, Map<String, String> message);
}
