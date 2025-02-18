package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.GameTask;
import com.techhunt.deckster.game.enums.GameState;
import com.techhunt.deckster.game.task.DraftGameTaskConfigurator;
import com.techhunt.deckster.game.task.ResponsesGameTaskConfigurator;
import com.techhunt.deckster.game.task.SetupGameTaskConfigurator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskClient implements TaskService {

    private final DraftGameTaskConfigurator draftGameTaskConfigurator;
    private final SetupGameTaskConfigurator setupGameTaskConfigurator;
    private final ResponsesGameTaskConfigurator responsesGameTaskConfigurator;

    @Override
    public GameTask getTask(GameState gameState, Map<String, String> message) {
        return switch (gameState) {
            case DRAFT -> draftGameTaskConfigurator.getTask(message);
            case SETUP -> setupGameTaskConfigurator.getTask(message);
            case RESPONSES -> responsesGameTaskConfigurator.getTask(message);
            default -> {
                log.error("Invalid game state: {}", gameState);
                yield null;
            }
        };
    }
}
