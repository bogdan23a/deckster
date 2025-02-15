package com.techhunt.deckster.game.guard;

import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.enums.GameState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

public class IsEndGameGuard implements Guard<GameState, GameEvent> {

    @Override
    public boolean evaluate(StateContext<GameState, GameEvent> context) {
        return false;
    }
}
