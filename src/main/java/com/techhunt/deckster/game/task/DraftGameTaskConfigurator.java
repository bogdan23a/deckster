package com.techhunt.deckster.game.task;

import com.techhunt.deckster.game.entity.GameTask;
import com.techhunt.deckster.game.entity.Player;
import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.techhunt.deckster.game.enums.GameEvent.SET_GAME;
import static com.techhunt.deckster.game.service.GameClient.EMAIL_HEADER;
import static com.techhunt.deckster.game.service.GameClient.GAME_ID_HEADER;
import static com.techhunt.deckster.game.task.GameTaskFieldLabel.PLAYERS;
import static com.techhunt.deckster.game.task.GameTaskFieldLabel.SHARE_GAME;
import static com.techhunt.deckster.game.task.InputType.LINK;
import static com.techhunt.deckster.game.task.InputType.LIST;

@Service
@RequiredArgsConstructor
public class DraftGameTaskConfigurator extends GameTaskConfigurator {

    private final PlayerService playerService;

    public List<GameTaskDetail> setupDetails(Map<String, String> message) {
        UUID gameId = UUID.fromString(message.get(GAME_ID_HEADER));
        List<Player> players = playerService.findByGameId(gameId);
        return List.of(
                GameTaskDetail.builder().order(1).label(SHARE_GAME).input(GameTaskInput.builder().inputType(LINK).values(null).build()).build(),
                GameTaskDetail.builder().order(2).label(PLAYERS).input(GameTaskInput.builder().inputType(LIST).values(players).build()).build()
        );
    }

    public List<GameEvent> setupEvents(Map<String, String> message) {
        String email = message.get(EMAIL_HEADER);
        Player player = playerService.findByEmail(email);
        if (!player.isHost()) {
            return List.of();
        }
        return List.of(SET_GAME);
    }

    @Override
    public GameTask getTask(Map<String, String> message) {
        return new GameTask(setupDetails(message), setupEvents(message));
    }
}
