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
import static com.techhunt.deckster.game.task.GameTaskFieldLabel.PLAYERS;
import static com.techhunt.deckster.game.task.InputType.LIST;

@Service
@RequiredArgsConstructor
public class DraftGameTaskConfigurator extends GameTaskConfigurator {

    private final PlayerService playerService;

    public Map<String, GameTaskFieldValue> setupDisplay(UUID gameId) {
        List<Player> players = playerService.findByGameId(gameId);
        return Map.of(PLAYERS.getMessage(), new DraftTaskFieldValue(LIST, players));
    }

    public List<GameEvent> setupEvents() {
        return List.of(SET_GAME);
    }

    @Override
    public GameTask getTask(UUID gameId) {
        return new GameTask(setupDisplay(gameId), setupEvents());
    }
}
