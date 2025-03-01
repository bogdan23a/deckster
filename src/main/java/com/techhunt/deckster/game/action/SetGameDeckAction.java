package com.techhunt.deckster.game.action;

import com.techhunt.deckster.game.entity.Deck;
import com.techhunt.deckster.game.entity.Game;
import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.enums.GameState;
import com.techhunt.deckster.game.service.DeckService;
import com.techhunt.deckster.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.techhunt.deckster.game.service.GameClient.DECK_ID_HEADER;
import static com.techhunt.deckster.game.service.GameClient.GAME_ID_HEADER;

@Service
@RequiredArgsConstructor
public class SetGameDeckAction implements Action<GameState, GameEvent> {

    private final GameService gameService;
    private final DeckService deckService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void execute(StateContext<GameState, GameEvent> context) {
        String gameId = (String) context.getMessageHeader(GAME_ID_HEADER);
        String deckId = (String) context.getMessageHeader(DECK_ID_HEADER);
        Deck deck = deckService.findById(UUID.fromString(deckId));
        Game game = gameService.findById(UUID.fromString(gameId));
        game.setDeck(deck);
        gameService.save(game);
        simpMessagingTemplate.convertAndSend("/public", "");
    }
}
