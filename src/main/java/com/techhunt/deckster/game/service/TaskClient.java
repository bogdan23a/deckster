package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.GameTask;
import com.techhunt.deckster.game.enums.GameState;
import com.techhunt.deckster.game.task.DraftGameTaskConfigurator;
import com.techhunt.deckster.game.task.SetupGameTaskConfigurator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.techhunt.deckster.game.enums.GameState.DRAFT;
import static com.techhunt.deckster.game.enums.GameState.SETUP;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskClient implements TaskService {

    private final DraftGameTaskConfigurator draftGameTaskConfigurator;
    private final SetupGameTaskConfigurator setupGameTaskConfigurator;

    public Map<GameState, GameTask> tasks(UUID gameId) {
        Map<GameState, GameTask> tasks = new HashMap<>();
        tasks.put(DRAFT, draftGameTaskConfigurator.getTask(gameId));
        tasks.put(SETUP, setupGameTaskConfigurator.getTask(gameId));
        return tasks;
    }

    @Override
    public GameTask getTask(UUID gameId, GameState gameState) {
        return tasks(gameId).get(gameState);
    }
}
