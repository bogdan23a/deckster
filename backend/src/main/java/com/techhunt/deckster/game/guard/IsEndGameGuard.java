package com.techhunt.deckster.game.guard;

import com.techhunt.deckster.game.entity.Player;
import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.enums.GameState;
import com.techhunt.deckster.game.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.techhunt.deckster.game.service.GameClient.GAME_ID_HEADER;

@Service
@RequiredArgsConstructor
public class IsEndGameGuard implements Guard<GameState, GameEvent> {

    private final PlayerService playerService;

    @Override
    public boolean evaluate(StateContext<GameState, GameEvent> context) {
        String gameId = (String) context.getMessageHeader(GAME_ID_HEADER);
        List<Player> players =  playerService.findByGameId(UUID.fromString(gameId));
        return players.stream().anyMatch(Player::isWinner);
    }
}
