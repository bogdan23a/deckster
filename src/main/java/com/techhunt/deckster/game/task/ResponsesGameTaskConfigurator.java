package com.techhunt.deckster.game.task;

import com.techhunt.deckster.game.entity.Card;
import com.techhunt.deckster.game.entity.Game;
import com.techhunt.deckster.game.entity.GameCard;
import com.techhunt.deckster.game.entity.Player;
import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.service.CardService;
import com.techhunt.deckster.game.service.GameService;
import com.techhunt.deckster.game.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
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
import static com.techhunt.deckster.game.task.GameTaskFieldLabel.OTHER_PLAYERS_TURN;
import static com.techhunt.deckster.game.task.GameTaskFieldLabel.SCORE;
import static com.techhunt.deckster.game.task.InputType.CARD_PICKER;

@Service
@RequiredArgsConstructor
public class ResponsesGameTaskConfigurator extends GameTaskConfigurator {

    private final GameService gameService;
    private final CardService cardService;
    private final PlayerService playerService;

    @Override
    public List<GameTaskDetail> setupDetails(Map<String, String> message) {
        String gameId = message.get(GAME_ID_HEADER);
        String email = message.get(EMAIL_HEADER);
        Game game = gameService.findById(UUID.fromString(gameId));
        Player player = playerService.findByEmail(email);
        Card prompt = gameService.getPrompt(UUID.fromString(gameId));
        if (player.isCzar()) {
            return List.of(
                    GameTaskDetail.builder()
                            .order(1)
                            .label(OTHER_PLAYERS_TURN)
                            .input(GameTaskInput.builder().inputType(InputType.PROMPT).values(List.of(prompt)).build())
                            .build()
            );
        }
        player = game.getPlayers().stream().filter(gamePlayer -> gamePlayer.getEmail().equals(email))
                .findFirst().orElse(null);
        List<Card> hand = player.getHand().stream()
                .filter(Predicate.not(GameCard::isUsed))
                .map(GameCard::getCardId)
                .map(cardService::findById)
                .filter(card -> Objects.equals(card.getType().getName(), "Response"))
                .toList();
        List<Pair<String, String>> scoreMap = gameService.findById(UUID.fromString(gameId))
                .getPlayers()
                .stream()
                .map(p -> Pair.of(p.getEmail(), String.valueOf(p.getScore())))
                .toList();
        return List.of(
                GameTaskDetail.builder()
                        .order(1)
                        .label(PROMPT)
                        .input(GameTaskInput.builder().inputType(InputType.PROMPT).values(List.of(prompt)).build())
                        .build(),
                GameTaskDetail.builder()
                        .order(2)
                        .label(RESPONSES)
                        .input(GameTaskInput.builder().inputType(CARD_PICKER).values(hand).build())
                        .build(),
                GameTaskDetail.builder()
                        .order(3)
                        .label(SCORE)
                        .input(GameTaskInput.builder().inputType(InputType.SCORE).values(scoreMap).build())
                        .build()
        );
    }

    @Override
    public List<GameEvent> setupEvents(Map<String, String> message) {
        String email = message.get(EMAIL_HEADER);
        Player player = playerService.findByEmail(email);
        if (player.isCzar()) {
            return List.of();
        }
        return List.of(RESPOND);
    }
}
