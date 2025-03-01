package com.techhunt.deckster.game.action;

import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.enums.GameState;
import com.techhunt.deckster.game.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.techhunt.deckster.game.service.GameClient.EMAIL_HEADER;
import static com.techhunt.deckster.game.service.GameClient.GAME_ID_HEADER;

@Slf4j
@Service
@RequiredArgsConstructor
public class SetRoundPromptAction implements Action<GameState, GameEvent> {

    private final GameService gameService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void execute(StateContext<GameState, GameEvent> context) {
        try {
            String gameId = (String) context.getMessageHeader(GAME_ID_HEADER);
            String email = (String) context.getMessageHeader(EMAIL_HEADER);
            gameService.choosePrompt(UUID.fromString(gameId), email);
            simpMessagingTemplate.convertAndSend("/public", "");
        } catch (Exception e) {
            log.error("Error setting round prompt", e);
        }
    }
}
