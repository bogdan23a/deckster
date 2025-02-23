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
import java.util.function.Predicate;

import static com.techhunt.deckster.game.enums.GameEvent.RESPOND;
import static com.techhunt.deckster.game.service.GameClient.EMAIL_HEADER;
import static com.techhunt.deckster.game.service.GameClient.GAME_ID_HEADER;
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
        String gameId = message.get(GAME_ID_HEADER);
        String email = message.get(EMAIL_HEADER);
        Game game = gameService.findById(UUID.fromString(gameId));
        Player player = game.getPlayers().stream().filter(gamePlayer -> gamePlayer.getEmail().equals(email))
                .findFirst().orElse(null);
        List<Card> hand = player.getHand().stream()
                .filter(Predicate.not(GameCard::isUsed))
                .map(GameCard::getCardId)
                .map(cardService::findById)
                .filter(card -> Objects.equals(card.getType().getName(), "Response"))
                .toList();
        Card prompt = gameService.getPrompt(UUID.fromString(gameId));
        return Map.of(
                PROMPT.getMessage(), new ResponseTaskFieldValue(InputType.PROMPT, List.of(prompt)),
                RESPONSES.getMessage(), new ResponseTaskFieldValue(CARD_PICKER, hand));
    }

    @Override
    public List<GameEvent> setupEvents() {
        return List.of(RESPOND);
    }
}
