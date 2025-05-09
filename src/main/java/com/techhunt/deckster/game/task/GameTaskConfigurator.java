package com.techhunt.deckster.game.task;

import com.techhunt.deckster.game.entity.GameTask;
import com.techhunt.deckster.game.enums.GameEvent;

import java.util.List;
import java.util.Map;

public abstract class GameTaskConfigurator {

    public GameTask getTask(Map<String, String> message) {
        return new GameTask(setupDetails(message), setupEvents(message));
    }

    public abstract List<GameTaskDetail> setupDetails(Map<String, String> message);
    public abstract List<GameEvent> setupEvents(Map<String, String> message);
}
