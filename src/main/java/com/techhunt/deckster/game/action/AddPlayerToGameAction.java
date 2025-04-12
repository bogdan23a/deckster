package com.techhunt.deckster.game.action;

import com.techhunt.deckster.game.entity.Player;
import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.enums.GameState;
import com.techhunt.deckster.game.service.GameCardService;
import com.techhunt.deckster.game.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

import static com.techhunt.deckster.game.service.GameClient.EMAIL_HEADER;
import static com.techhunt.deckster.game.service.GameClient.GAME_ID_HEADER;

@Service
@RequiredArgsConstructor
public class AddPlayerToGameAction implements Action<GameState, GameEvent> {

    private final PlayerService playerService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final GameCardService gameCardService;

    @Override
    public void execute(StateContext<GameState, GameEvent> context) {
        String email = (String) context.getMessageHeader(EMAIL_HEADER);
        String gameId = (String) context.getMessageHeader(GAME_ID_HEADER);
        Player player = playerService.findByEmail(email);
        if (player == null) {
            player = new Player(email);
        }
        player.setGameId(UUID.fromString(gameId));
        player.setCzar(false);
        gameCardService.removeAll(player.getHand() != null ? player.getHand() : Set.of());
        player.setHand(null);
        playerService.save(player);
        simpMessagingTemplate.convertAndSend("/public", "x");
    }
}
