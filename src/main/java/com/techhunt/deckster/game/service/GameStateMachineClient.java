package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.Game;
import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.enums.GameState;
import com.techhunt.deckster.game.interceptor.GameStateMachineInterceptor;
import com.techhunt.deckster.game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

import static com.techhunt.deckster.game.service.GameClient.GAME_ID_HEADER;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameStateMachineClient implements GameStateMachineService {

    private final GameRepository repository;
    private final GameStateMachineInterceptor gameStateMachineInterceptor;
    private final StateMachineFactory<GameState, GameEvent> stateMachineFactory;

    @Override
    public void sendEvent(GameEvent event, Map<String, String> message) {
        UUID gameId = UUID.fromString(message.get(GAME_ID_HEADER));
        StateMachine<GameState, GameEvent> stateMachine = build(gameId);
        sendEvent(message, stateMachine, event);
    }

    @Override
    public StateMachine<GameState, GameEvent> build(UUID gameId) {
        Game game = repository.getReferenceById(gameId);
        StateMachine<GameState, GameEvent> stateMachine = stateMachineFactory.getStateMachine(game.getId());
        stateMachine.stopReactively().subscribe();
        stateMachine.getStateMachineAccessor().doWithAllRegions(accessor -> {
            accessor.addStateMachineInterceptor(gameStateMachineInterceptor);
            accessor.resetStateMachineReactively(new DefaultStateMachineContext<>(game.getState(), null, null, null)).subscribe();
        });
        stateMachine.startReactively().subscribe();
        return stateMachine;
    }

    private void sendEvent(Map<String, String> messageBody, StateMachine<GameState, GameEvent> stateMachine, GameEvent gameEvent) {
        MessageBuilder<GameEvent> message = MessageBuilder.withPayload(gameEvent);
        messageBody.forEach(message::setHeader);
        stateMachine.sendEvent(Mono.just(message.build())).subscribe();
    }
}
