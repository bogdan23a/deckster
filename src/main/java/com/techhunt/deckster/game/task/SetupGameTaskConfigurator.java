package com.techhunt.deckster.game.task;

import com.techhunt.deckster.game.entity.Player;
import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.service.DeckService;
import com.techhunt.deckster.game.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.techhunt.deckster.game.enums.GameEvent.GAME_SET;
import static com.techhunt.deckster.game.service.GameClient.EMAIL_HEADER;
import static com.techhunt.deckster.game.task.GameTaskFieldLabel.DECK;
import static com.techhunt.deckster.game.task.GameTaskFieldLabel.HOST_SETUP;
import static com.techhunt.deckster.game.task.InputType.DECK_PICKER;

@Service
@RequiredArgsConstructor
public class SetupGameTaskConfigurator extends GameTaskConfigurator {

    private final DeckService deckService;
    private final PlayerService playerService;

    public Map<String, GameTaskFieldValue> setupDisplay(Map<String, String> message) {
        String email = message.get(EMAIL_HEADER);
        Player player = playerService.findByEmail(email);
        if (!player.isHost()) {
            return Map.of(HOST_SETUP.getMessage(), new SetupTaskFieldValue());
        }
        List<?> decks = deckService.findAll();
        return Map.of(DECK.getMessage(), new SetupTaskFieldValue(DECK_PICKER, decks));
    }

    public List<GameEvent> setupEvents(Map<String, String> message) {
        String email = message.get(EMAIL_HEADER);
        Player player = playerService.findByEmail(email);
        if (!player.isHost()) {
            return List.of();
        }
        return List.of(GAME_SET);
    }
}
