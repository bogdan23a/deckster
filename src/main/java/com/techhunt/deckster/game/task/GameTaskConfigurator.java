package com.techhunt.deckster.game.task;

import com.techhunt.deckster.game.entity.GameTask;
import com.techhunt.deckster.game.enums.GameEvent;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class GameTaskConfigurator {


    public GameTask getTask(UUID gameId) {
        return new GameTask(setupDisplay(gameId), setupEvents());
    }

    public abstract Map<String, GameTaskFieldValue> setupDisplay(UUID gameId);
    public abstract List<GameEvent> setupEvents();
}
