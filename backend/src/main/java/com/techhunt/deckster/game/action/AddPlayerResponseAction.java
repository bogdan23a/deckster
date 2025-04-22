package com.techhunt.deckster.game.action;

import com.techhunt.deckster.game.entity.GameCard;
import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.enums.GameState;
import com.techhunt.deckster.game.service.GameCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static com.techhunt.deckster.game.service.GameClient.CARD_IDS_HEADER;
import static com.techhunt.deckster.game.service.GameClient.EMAIL_HEADER;
import static com.techhunt.deckster.game.service.GameClient.GAME_ID_HEADER;

@Service
@RequiredArgsConstructor
public class AddPlayerResponseAction implements Action<GameState, GameEvent> {

    private final GameCardService gameCardService;

    @Override
    public void execute(StateContext<GameState, GameEvent> context) {
        AtomicInteger index = new AtomicInteger();
        String gameId = (String) context.getMessageHeader(GAME_ID_HEADER);
        String cardIds = (String) context.getMessageHeader(CARD_IDS_HEADER);
        String email = (String) context.getMessageHeader(EMAIL_HEADER);
        UUID responseGroup = UUID.randomUUID();
        Arrays.stream(cardIds.split(","))
                .forEach(id -> {
                    GameCard card = gameCardService.findByGameIdAndCardId(UUID.fromString(gameId), UUID.fromString(id));
                    card.setUsed(true);
                    card.setUsedAt(Timestamp.from(Instant.now()));
                    card.setEmail(email);
                    card.setResponseOrder(index.getAndIncrement());
                    card.setResponseGroup(responseGroup);
                    gameCardService.save(card);
                });
    }
}
