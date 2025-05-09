package com.techhunt.deckster.game.action;

import com.techhunt.deckster.game.aspect.RefreshNotification;
import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.enums.GameState;
import com.techhunt.deckster.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.techhunt.deckster.game.service.GameClient.GAME_ID_HEADER;

@Service
@RequiredArgsConstructor
public class ResetGameCardsAction implements Action<GameState, GameEvent> {

    private final GameService gameService;

    @Override
    @RefreshNotification
    public void execute(StateContext<GameState, GameEvent> context) {
        String gameId = (String) context.getMessageHeader(GAME_ID_HEADER);
        gameService.removeUsedCardsFromUsersHands(UUID.fromString(gameId));
    }
}
