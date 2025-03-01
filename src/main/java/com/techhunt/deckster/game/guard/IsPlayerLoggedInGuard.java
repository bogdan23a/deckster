package com.techhunt.deckster.game.guard;

import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.enums.GameState;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Service;

import static com.techhunt.deckster.game.service.GameClient.EMAIL_HEADER;

@Service
@RequiredArgsConstructor
public class IsPlayerLoggedInGuard implements Guard<GameState, GameEvent> {

    @Override
    public boolean evaluate(StateContext<GameState, GameEvent> context) {
        String email = (String) context.getMessageHeader(EMAIL_HEADER);
        return !email.isEmpty();
    }
}
