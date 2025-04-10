package com.techhunt.deckster.game.action;

import com.techhunt.deckster.game.entity.Player;
import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.enums.GameState;
import com.techhunt.deckster.game.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Service;

import static com.techhunt.deckster.game.service.GameClient.EMAIL_HEADER;

@Service
@RequiredArgsConstructor
public class ResetPlayerScoreAction implements Action<GameState, GameEvent> {
    private final PlayerService playerService;

    @Override
    public void execute(StateContext<GameState, GameEvent> context) {
        String email = (String) context.getMessageHeader(EMAIL_HEADER);
        Player player = playerService.findByEmail(email);
        player.setScore(0);
        playerService.save(player);
    }
}
