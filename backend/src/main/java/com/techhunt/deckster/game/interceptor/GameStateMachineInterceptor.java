package com.techhunt.deckster.game.interceptor;

import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.enums.GameState;
import com.techhunt.deckster.game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static com.techhunt.deckster.game.service.GameClient.GAME_ID_HEADER;

@Slf4j
@Component
@RequiredArgsConstructor
public class GameStateMachineInterceptor extends StateMachineInterceptorAdapter<GameState, GameEvent> {

    private final GameRepository repository;

    @Override
    public Message<GameEvent> preEvent(Message<GameEvent> message, StateMachine<GameState, GameEvent> stateMachine) {
        return message;
    }

    @Override
    public void preStateChange(State<GameState, GameEvent> state, Message<GameEvent> message, Transition<GameState, GameEvent> transition, StateMachine<GameState, GameEvent> stateMachine, StateMachine<GameState, GameEvent> rootStateMachine) {
        Optional.ofNullable(message)
                .map(msg -> UUID.fromString((String) message.getHeaders().getOrDefault(GAME_ID_HEADER, null)))
                .map(repository::findById)
                .ifPresent(game -> {
                    if (game.isEmpty()) {
                        return;
                    }
                    game.get().setState(state.getId());
                    repository.save(game.get());
                });
    }

    @Override
    public void postStateChange(State<GameState, GameEvent> state, Message<GameEvent> message, Transition<GameState, GameEvent> transition, StateMachine<GameState, GameEvent> stateMachine, StateMachine<GameState, GameEvent> rootStateMachine) {
        log.debug("State change to " + state.getId());
    }

    @Override
    public StateContext<GameState, GameEvent> preTransition(StateContext<GameState, GameEvent> stateContext) {
        return stateContext;
    }

    @Override
    public StateContext<GameState, GameEvent> postTransition(StateContext<GameState, GameEvent> stateContext) {
        return stateContext;
    }

    @Override
    public Exception stateMachineError(StateMachine<GameState, GameEvent> stateMachine, Exception exception) {
        return super.stateMachineError(stateMachine, exception);
    }
}
