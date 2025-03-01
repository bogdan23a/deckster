package com.techhunt.deckster.game.task;

import com.techhunt.deckster.game.entity.Card;
import com.techhunt.deckster.game.entity.Player;
import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.service.CardTypeService;
import com.techhunt.deckster.game.service.GameCardService;
import com.techhunt.deckster.game.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.techhunt.deckster.game.enums.GameEvent.PICK_WINNER;
import static com.techhunt.deckster.game.service.GameClient.EMAIL_HEADER;
import static com.techhunt.deckster.game.service.GameClient.GAME_ID_HEADER;
import static com.techhunt.deckster.game.task.GameTaskFieldLabel.OTHER_PLAYERS_TURN;
import static com.techhunt.deckster.game.task.GameTaskFieldLabel.WINNER;

@Service
@RequiredArgsConstructor
public class RewardGameTaskConfigurator extends GameTaskConfigurator{

    private final GameCardService gameCardService;
    private final CardTypeService cardTypeService;
    private final PlayerService playerService;

    @Override
    public Map<String, GameTaskFieldValue> setupDisplay(Map<String, String> message) {
        String gameId = message.get(GAME_ID_HEADER);
        String email = message.get(EMAIL_HEADER);
        Player player = playerService.findByEmail(email);
        if (!player.isCzar()) {
            return Map.of(OTHER_PLAYERS_TURN.getMessage(), new RewardTaskFieldValue());
        }
        UUID responseCardType = cardTypeService.findByName("Response").getId();
        List<Card> responses = gameCardService.findbyGameIdAndCardTypeAndUsed(UUID.fromString(gameId), responseCardType, true);
        return Map.of(WINNER.getMessage(), new RewardTaskFieldValue(InputType.CARD_PICKER, responses));
    }

    @Override
    public List<GameEvent> setupEvents(Map<String, String> message) {
        String email = message.get(EMAIL_HEADER);
        Player player = playerService.findByEmail(email);
        if (!player.isCzar()) {
            return List.of();
        }
        return List.of(PICK_WINNER);
    }
}
