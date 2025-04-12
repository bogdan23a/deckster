package com.techhunt.deckster.game.action;

import com.techhunt.deckster.game.entity.GameCard;
import com.techhunt.deckster.game.entity.Player;
import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.enums.GameState;
import com.techhunt.deckster.game.service.GameCardService;
import com.techhunt.deckster.game.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.techhunt.deckster.game.service.GameClient.CARD_IDS_HEADER;
import static com.techhunt.deckster.game.service.GameClient.GAME_ID_HEADER;
import static com.techhunt.deckster.game.service.GameClient.RESPONSE_GROUP_HEADER;

@Service
@RequiredArgsConstructor
public class IncrementScoreAction implements Action<GameState, GameEvent> {

    private final GameCardService gameCardService;
    private final PlayerService playerService;

    @Override
    public void execute(StateContext<GameState, GameEvent> context) {
        String gameId = (String) context.getMessageHeader(GAME_ID_HEADER);
        String responseGroup = (String) context.getMessageHeader(RESPONSE_GROUP_HEADER);
        GameCard card = gameCardService.findOneByGameIdAndResponseGroup(UUID.fromString(gameId), UUID.fromString(responseGroup));
        Player player = playerService.findByEmail(card.getEmail());
        player.setScore(player.getScore() + 1);
        playerService.save(player);
    }
}
