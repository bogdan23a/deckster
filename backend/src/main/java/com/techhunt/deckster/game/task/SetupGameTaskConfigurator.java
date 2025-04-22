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

    public List<GameTaskDetail> setupDetails(Map<String, String> message) {
        String email = message.get(EMAIL_HEADER);
        Player player = playerService.findByEmail(email);
        if (!player.isHost()) {
            return List.of(
                    GameTaskDetail.builder()
                            .order(1)
                            .label(HOST_SETUP)
                            .input(GameTaskInput.builder().build())
                            .build()
            );
        }
        List<?> decks = deckService.findAll();
        return List.of(
                GameTaskDetail.builder()
                        .order(1)
                        .label(DECK)
                        .input(GameTaskInput.builder().inputType(DECK_PICKER).values(decks).build())
                        .build()
        );
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
