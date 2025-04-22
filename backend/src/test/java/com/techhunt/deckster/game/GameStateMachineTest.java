package com.techhunt.deckster.game;

import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.enums.GameState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import reactor.core.publisher.Mono;

import java.util.UUID;

@SpringBootTest
public class GameStateMachineTest {

    @Autowired
    StateMachineFactory<GameState, GameEvent> stateMachineFactory;

    @Test
    void test() {
//        StateMachine<GameState, GameEvent> stateMachine = stateMachineFactory.getStateMachine(UUID.randomUUID());
//
//        stateMachine.startReactively().subscribe((isStarted) -> System.out.println("Machine started " + isStarted));
//
//        stateMachine.sendEvent(Mono.just(MessageBuilder.withPayload(GameEvent.START).build())).doOnComplete(() -> System.out.println("Event sent")).doOnError((ex) -> System.out.println("event not sent " + ex.getMessage())).subscribe();
//
//        System.out.println(stateMachine.getState().toString());
    }
}
