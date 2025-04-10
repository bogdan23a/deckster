package com.techhunt.deckster.game.task;

import com.techhunt.deckster.game.entity.Card;
import com.techhunt.deckster.game.entity.Player;
import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.service.CardTypeService;
import com.techhunt.deckster.game.service.GameCardService;
import com.techhunt.deckster.game.service.GameService;
import com.techhunt.deckster.game.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.techhunt.deckster.game.enums.GameEvent.PICK_WINNER;
import static com.techhunt.deckster.game.service.GameClient.EMAIL_HEADER;
import static com.techhunt.deckster.game.service.GameClient.GAME_ID_HEADER;
import static com.techhunt.deckster.game.task.GameTaskFieldLabel.OTHER_PLAYERS_TURN;
import static com.techhunt.deckster.game.task.GameTaskFieldLabel.PROMPT;
import static com.techhunt.deckster.game.task.GameTaskFieldLabel.SCORE;
import static com.techhunt.deckster.game.task.GameTaskFieldLabel.WINNER;

@Service
@RequiredArgsConstructor
public class RewardGameTaskConfigurator extends GameTaskConfigurator{

    private final GameCardService gameCardService;
    private final CardTypeService cardTypeService;
    private final PlayerService playerService;
    private final GameService gameService;

    @Override
    public List<GameTaskDetail> setupDetails(Map<String, String> message) {
        String gameId = message.get(GAME_ID_HEADER);
        String email = message.get(EMAIL_HEADER);
        Player player = playerService.findByEmail(email);
        Card prompt = gameService.getPrompt(UUID.fromString(gameId));
        if (!player.isCzar()) {
            return List.of(
                    GameTaskDetail.builder()
                            .order(1)
                            .label(OTHER_PLAYERS_TURN)
                            .input(GameTaskInput.builder().inputType(InputType.PROMPT).values(List.of(prompt)).build())
                            .build()
            );
        }
        UUID responseCardType = cardTypeService.findByName("Response").getId();
        List<Card> responses = gameCardService.findbyGameIdAndCardTypeAndUsed(UUID.fromString(gameId), responseCardType, true);
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
                        .label(WINNER)
                        .input(GameTaskInput.builder().inputType(InputType.CARD_PICKER).values(responses).build())
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
        if (!player.isCzar()) {
            return List.of();
        }
        return List.of(PICK_WINNER);
    }
}
