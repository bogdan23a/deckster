package com.techhunt.deckster.game.task;

import com.techhunt.deckster.game.entity.Card;
import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.service.CardTypeService;
import com.techhunt.deckster.game.service.GameCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.techhunt.deckster.game.enums.GameEvent.PICK_WINNER;
import static com.techhunt.deckster.game.service.GameClient.GAME_ID_HEADER;
import static com.techhunt.deckster.game.task.GameTaskFieldLabel.WINNER;

@Service
@RequiredArgsConstructor
public class RewardGameTaskConfigurator extends GameTaskConfigurator{

    private final GameCardService gameCardService;
    private final CardTypeService cardTypeService;

    @Override
    public Map<String, GameTaskFieldValue> setupDisplay(Map<String, String> message) {
        String gameId = message.get(GAME_ID_HEADER);
        UUID responseCardType = cardTypeService.findByName("Response").getId();
        List<Card> responses = gameCardService.findbyGameIdAndCardTypeAndUsed(UUID.fromString(gameId), responseCardType, true);
        return Map.of(WINNER.getMessage(), new RewardTaskFieldValue(InputType.CARD_PICKER, responses));
    }

    @Override
    public List<GameEvent> setupEvents() {
        return List.of(PICK_WINNER);
    }
}
