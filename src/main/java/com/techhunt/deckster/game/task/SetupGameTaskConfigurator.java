package com.techhunt.deckster.game.task;

import com.techhunt.deckster.game.entity.Deck;
import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.service.DeckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.techhunt.deckster.game.enums.GameEvent.GAME_SET;
import static com.techhunt.deckster.game.task.GameTaskFieldLabel.DECK;
import static com.techhunt.deckster.game.task.InputType.PICKER;

@Service
@RequiredArgsConstructor
public class SetupGameTaskConfigurator extends GameTaskConfigurator {

    private final DeckService deckService;

    public Map<String, GameTaskFieldValue> setupDisplay(UUID gameId) {
        List<?> decks = deckService.findAll();
        decks.forEach(deck -> ((Deck) deck).setCards(null));
        return Map.of(DECK.getMessage(), new SetupTaskFieldValue(PICKER, decks));
    }

    public List<GameEvent> setupEvents() {
        return List.of(GAME_SET);
    }
}
