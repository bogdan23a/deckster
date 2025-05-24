package com.techhunt.deckster.game.task;

import com.techhunt.deckster.game.entity.Player;
import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.service.GameService;
import com.techhunt.deckster.game.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.techhunt.deckster.game.enums.GameEvent.END;
import static com.techhunt.deckster.game.service.GameClient.EMAIL_HEADER;
import static com.techhunt.deckster.game.service.GameClient.GAME_ID_HEADER;
import static com.techhunt.deckster.game.task.GameTaskFieldLabel.SCORE;
import static com.techhunt.deckster.game.task.GameTaskFieldLabel.THE_WINNER;
import static com.techhunt.deckster.game.task.GameTaskFieldLabel.YOU_WON;

@Service
@RequiredArgsConstructor
public class EndGameTaskConfigurator extends GameTaskConfigurator{
    private final PlayerService playerService;
    private final GameService gameService;

    @Override
    public List<GameTaskDetail> setupDetails(Map<String, String> message) {
        String gameId = message.get(GAME_ID_HEADER);
        String email = message.get(EMAIL_HEADER);
        Player player = playerService.findByEmail(email);
        List<Pair<String, String>> scoreMap = gameService.findById(UUID.fromString(gameId))
                .getPlayers()
                .stream()
                .map(p -> Pair.of(p.getEmail(), String.valueOf(p.getScore())))
                .sorted(Comparator.comparing(Pair::getSecond))
                .toList();
        List<GameTaskDetail> details = new ArrayList<>();
        if (player.isWinner()) {
            details.add(GameTaskDetail.builder()
                    .order(1)
                    .label(YOU_WON)
                    .build());
        } else {
            details.add(GameTaskDetail.builder()
                    .order(1)
                    .label(THE_WINNER)
                    .input(GameTaskInput.builder()
                            .inputType(InputType.SCORE)
                            .values(List.of(scoreMap.getFirst().getFirst()))
                            .build())
                    .build());
        }
        details.add(GameTaskDetail.builder()
                            .order(2)
                            .label(SCORE)
                            .input(GameTaskInput.builder().inputType(InputType.SCORE).values(scoreMap).build())
                            .build());
        return details;
    }

    @Override
    public List<GameEvent> setupEvents(Map<String, String> message) {
        return List.of(END);
    }
}
