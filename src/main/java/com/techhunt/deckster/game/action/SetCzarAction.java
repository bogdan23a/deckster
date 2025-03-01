package com.techhunt.deckster.game.action;

import com.techhunt.deckster.game.entity.Player;
import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.enums.GameState;
import com.techhunt.deckster.game.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static com.techhunt.deckster.game.service.GameClient.GAME_ID_HEADER;

@Service
@RequiredArgsConstructor
public class SetCzarAction implements Action<GameState, GameEvent> {

    private final PlayerService playerService;

    @Override
    public void execute(StateContext<GameState, GameEvent> context) {
        String gameId = (String) context.getMessage().getHeaders().get(GAME_ID_HEADER);
        List<Player> players = playerService.findByGameId(UUID.fromString(gameId));
        players.sort(Comparator.comparing(Player::getEmail));
        for (Player player : players) {
            if (player.isCzar()) {
                player.setCzar(false);
                playerService.save(player);
                int nextIndex = (players.indexOf(player) + 1) % players.size();
                Player nextPlayer = players.get(nextIndex);
                nextPlayer.setCzar(true);
                playerService.save(nextPlayer);
                break;
            }
        }
    }
}
