package com.techhunt.deckster.game.action;

import com.techhunt.deckster.game.entity.Game;
import com.techhunt.deckster.game.entity.Player;
import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.enums.GameState;
import com.techhunt.deckster.game.service.GameService;
import com.techhunt.deckster.game.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddPlayerToGameAction implements Action<GameState, GameEvent> {

    private final GameService gameService;
    private final PlayerService playerService;

    @Override
    public void execute(StateContext<GameState, GameEvent> context) {
        String email = (String) context.getMessageHeader("email");
        String gameId = (String) context.getMessageHeader("gameId");
        Game game = gameService.findById(UUID.fromString(gameId));
        Player player = playerService.findByEmail(email);
        game.getPlayers().add(player);
        gameService.save(game);
    }
}
