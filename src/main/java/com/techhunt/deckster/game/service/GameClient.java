package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.Game;
import com.techhunt.deckster.game.entity.Player;
import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.enums.GameState;
import com.techhunt.deckster.game.interceptor.GameStateMachineInterceptor;
import com.techhunt.deckster.game.repository.GameRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static com.techhunt.deckster.game.enums.GameState.DRAFT;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameClient implements GameService {

    public static final String GAME_ID_HEADER = "game_id";

    private final GameRepository repository;
    private final PlayerService playerService;
    private final StateMachineFactory<GameState, GameEvent> stateMachineFactory;
    private final GameStateMachineInterceptor gameStateMachineInterceptor;

    @Override
    public List<Game> findAll() {
        return repository.findAll();
    }

    @Override
    public Game newGame(String email) {
        Game game = repository.save(new Game(DRAFT));
        playerService.save(new Player(email, game.getId(), true));
        return game;
    }

    @Transactional
    public void sendEvent(String event, String gameId) {
        StateMachine<GameState, GameEvent> stateMachine = build(gameId);
        sendEvent(UUID.fromString(gameId), stateMachine, GameEvent.valueOf(event));
    }

    @Override
    public Game findById(UUID gameId) {
        return repository.findById(gameId).orElse(null);
    }

    public StateMachine<GameState, GameEvent> build(String gameId) {
        Game game = repository.getReferenceById(UUID.fromString(gameId));
        StateMachine<GameState, GameEvent> stateMachine = stateMachineFactory.getStateMachine(game.getId());
        stateMachine.stopReactively().subscribe();
        stateMachine.getStateMachineAccessor().doWithAllRegions(accessor -> {
            accessor.addStateMachineInterceptor(gameStateMachineInterceptor);
            accessor.resetStateMachineReactively(new DefaultStateMachineContext<>(game.getState(), null, null, null)).subscribe();
        });
        stateMachine.startReactively().subscribe();
        return stateMachine;
    }

    private void sendEvent(UUID gameId, StateMachine<GameState, GameEvent> stateMachine, GameEvent gameEvent) {
        Message<GameEvent> message = MessageBuilder.withPayload(gameEvent)
                .setHeader(GAME_ID_HEADER, gameId.toString())
                .build();
        stateMachine.sendEvent(Mono.just(message)).subscribe();
        log.info(stateMachine.getState().toString());
    }
}
