package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.enums.GameState;
import org.springframework.statemachine.StateMachine;

import java.util.Map;
import java.util.UUID;

public interface GameStateMachineService {

    StateMachine<GameState, GameEvent> build(UUID gameId);
    void sendEvent(GameEvent event, Map<String, String> message);
}
