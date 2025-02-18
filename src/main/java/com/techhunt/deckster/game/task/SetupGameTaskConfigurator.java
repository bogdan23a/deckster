package com.techhunt.deckster.game.task;

import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.service.DeckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.techhunt.deckster.game.enums.GameEvent.GAME_SET;
import static com.techhunt.deckster.game.task.GameTaskFieldLabel.DECK;
import static com.techhunt.deckster.game.task.InputType.DECK_PICKER;

@Service
@RequiredArgsConstructor
public class SetupGameTaskConfigurator extends GameTaskConfigurator {

    private final DeckService deckService;

    public Map<String, GameTaskFieldValue> setupDisplay(Map<String, String> message) {
        List<?> decks = deckService.findAll();
        return Map.of(DECK.getMessage(), new SetupTaskFieldValue(DECK_PICKER, decks));
    }

    public List<GameEvent> setupEvents() {
        return List.of(GAME_SET);
    }
}
