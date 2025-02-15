package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.Game;
import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.enums.GameState;
import com.techhunt.deckster.game.interceptor.GameStateMachineInterceptor;
import com.techhunt.deckster.game.repository.GameRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameStateMachineService {

    public static final String GAME_ID_HEADER = "game_id";

    @Autowired
    GameRepository repository;

    @Autowired
    private final StateMachineFactory<GameState, GameEvent> stateMachineFactory;

    @Transactional
    public void createNew(String game) {
        StateMachine<GameState, GameEvent> stateMachine = stateMachineFactory.getStateMachine(game);
        stateMachine.startReactively().subscribe();
    }

    @Transactional
    public void sendEvent(String event, String gameId) {
        StateMachine<GameState, GameEvent> stateMachine = build(gameId);
        sendEvent(UUID.fromString(gameId), stateMachine, GameEvent.valueOf(event));
    }

    public StateMachine<GameState, GameEvent> build(String gameId) {
        Game game = repository.getReferenceById(UUID.fromString(gameId));
        StateMachine<GameState, GameEvent> stateMachine = stateMachineFactory.getStateMachine(gameId);
        stateMachine.stopReactively().subscribe();
        stateMachine.getStateMachineAccessor().doWithAllRegions(accessor -> {
            accessor.addStateMachineInterceptor(new GameStateMachineInterceptor(repository));
            accessor.resetStateMachineReactively(new DefaultStateMachineContext<>(game.getState(), null, null, null)).subscribe();
        });
        stateMachine.startReactively().subscribe();
        return stateMachine;
    }

    private void sendEvent(UUID gameId, StateMachine<GameState, GameEvent> stateMachine, GameEvent gameEvent) {
        Message<GameEvent> message = MessageBuilder.withPayload(gameEvent)
                .setHeader(GAME_ID_HEADER, gameId.toString())
                .build();
        stateMachine.sendEvent(Mono.just(message)).blockFirst();
    }
}
