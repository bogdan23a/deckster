package com.techhunt.deckster.game.task;

import com.techhunt.deckster.game.entity.Card;
import com.techhunt.deckster.game.entity.Game;
import com.techhunt.deckster.game.entity.GameCard;
import com.techhunt.deckster.game.entity.Player;
import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.service.CardService;
import com.techhunt.deckster.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static com.techhunt.deckster.game.enums.GameEvent.RESPOND;
import static com.techhunt.deckster.game.task.GameTaskFieldLabel.PROMPT;
import static com.techhunt.deckster.game.task.GameTaskFieldLabel.RESPONSES;
import static com.techhunt.deckster.game.task.InputType.CARD_PICKER;

@Service
@RequiredArgsConstructor
public class ResponsesGameTaskConfigurator extends GameTaskConfigurator {

    private final GameService gameService;
    private final CardService cardService;

    @Override
    public Map<String, GameTaskFieldValue> setupDisplay(Map<String, String> message) {
        String gameId = message.get("game_id");
        String email = message.get("email");
        Game game = gameService.findById(UUID.fromString(gameId));
        Player player = game.getPlayers().stream().filter(gamePlayer -> gamePlayer.getEmail().equals(email))
                .findFirst().orElse(null);
        List<Card> hand = player.getHand().stream()
                .map(GameCard::getCardId)
                .map(cardService::findById)
                .filter(card -> Objects.equals(card.getType().getName(), "Response"))
                .toList();
        return Map.of(
                PROMPT.getMessage(), new ResponseTaskFieldValue(InputType.PROMPT, List.of(gameService.getPrompt(UUID.fromString(gameId)))),
                RESPONSES.getMessage(), new ResponseTaskFieldValue(CARD_PICKER, hand));
    }

    @Override
    public List<GameEvent> setupEvents() {
        return List.of(RESPOND);
    }
}
