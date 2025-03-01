package com.techhunt.deckster.game.guard;

import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.enums.GameState;
import com.techhunt.deckster.game.service.CardTypeService;
import com.techhunt.deckster.game.service.GameCardService;
import com.techhunt.deckster.game.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.techhunt.deckster.game.service.GameClient.GAME_ID_HEADER;

@Service
@RequiredArgsConstructor
public class IsAllPlayersRespondedGuard implements Guard<GameState, GameEvent> {

    private final PlayerService playerService;
    private final GameCardService gameCardService;
    private final CardTypeService cardTypeService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public boolean evaluate(StateContext<GameState, GameEvent> context) {
        String gameId = (String) context.getMessageHeader(GAME_ID_HEADER);
        UUID responseType = cardTypeService.findByName("Response").getId();
        int playerCount = playerService.countByGameId(UUID.fromString(gameId));
        int roundResponses = gameCardService.countByGameIdAndType(UUID.fromString(gameId), responseType);
        boolean isAllPlayersResponded = roundResponses >= playerCount - 1;
        if (isAllPlayersResponded) {
            simpMessagingTemplate.convertAndSend("/public", "");
            return true;
        }
        return false;
    }
}
