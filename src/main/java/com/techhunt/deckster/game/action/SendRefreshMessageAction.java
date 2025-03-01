package com.techhunt.deckster.game.action;

import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.enums.GameState;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendRefreshMessageAction implements Action<GameState, GameEvent> {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void execute(StateContext<GameState, GameEvent> context) {
        simpMessagingTemplate.convertAndSend("/public", "");
    }
}
