package com.techhunt.deckster.game.task;

import com.techhunt.deckster.game.enums.GameEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EndGameTaskConfigurator extends GameTaskConfigurator{
    @Override
    public List<GameTaskDetail> setupDetails(Map<String, String> message) {
        return List.of();
    }

    @Override
    public List<GameEvent> setupEvents(Map<String, String> message) {
        return List.of();
    }
}
