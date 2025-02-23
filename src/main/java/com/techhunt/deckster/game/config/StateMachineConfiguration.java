package com.techhunt.deckster.game.config;

import com.techhunt.deckster.game.action.AddPlayerResponseAction;
import com.techhunt.deckster.game.action.AddPlayerToGameAction;
import com.techhunt.deckster.game.action.DealCardsAction;
import com.techhunt.deckster.game.action.IncrementScoreAction;
import com.techhunt.deckster.game.action.ResetGameCardsAction;
import com.techhunt.deckster.game.action.SetGameDeckAction;
import com.techhunt.deckster.game.action.SetRoundPromptAction;
import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.enums.GameState;
import com.techhunt.deckster.game.guard.IsAllPlayersRespondedGuard;
import com.techhunt.deckster.game.guard.IsEndGameGuard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

import static com.techhunt.deckster.game.enums.GameEvent.GAME_SET;
import static com.techhunt.deckster.game.enums.GameEvent.JOIN;
import static com.techhunt.deckster.game.enums.GameEvent.PICK_WINNER;
import static com.techhunt.deckster.game.enums.GameEvent.RESPOND;
import static com.techhunt.deckster.game.enums.GameEvent.SET_GAME;
import static com.techhunt.deckster.game.enums.GameState.DEAL;
import static com.techhunt.deckster.game.enums.GameState.DRAFT;
import static com.techhunt.deckster.game.enums.GameState.END_GAME;
import static com.techhunt.deckster.game.enums.GameState.PROMPT;
import static com.techhunt.deckster.game.enums.GameState.RESPONSES;
import static com.techhunt.deckster.game.enums.GameState.RESPONSES_CHOICE;
import static com.techhunt.deckster.game.enums.GameState.REWARD;
import static com.techhunt.deckster.game.enums.GameState.SETUP;
import static com.techhunt.deckster.game.enums.GameState.WINNER;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableStateMachineFactory
public class StateMachineConfiguration extends StateMachineConfigurerAdapter<GameState, GameEvent> {

    private final SetGameDeckAction setGameDeckAction;
    private final DealCardsAction dealCardsAction;
    private final SetRoundPromptAction setRoundPromptAction;
    private final AddPlayerToGameAction addPlayerToGameAction;
    private final IsAllPlayersRespondedGuard isAllPlayersRespondedGuard;
    private final AddPlayerResponseAction addPlayerResponseAction;
    private final IncrementScoreAction incrementScoreAction;
    private final ResetGameCardsAction resetGameCardsAction;

    @Override
    public void configure(StateMachineStateConfigurer<GameState, GameEvent> configurer) throws Exception {
        configurer.withStates()
                .initial(DRAFT)
                .states(EnumSet.allOf(GameState.class))
                .choice(WINNER)
                .choice(RESPONSES_CHOICE)
                .end(END_GAME);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<GameState, GameEvent> configurer) throws Exception {
        configurer.withExternal()
                    .source(DRAFT).target(SETUP).event(SET_GAME).and()
                .withExternal()
                    .source(DRAFT).target(DRAFT).action(addPlayerToGameAction).event(JOIN).and()
                .withExternal()
                    .source(SETUP).target(DEAL).action(setGameDeckAction).event(GAME_SET).and()
                .withExternal()
                    .source(DEAL).target(PROMPT).action(dealCardsAction).and()
                .withExternal()
                    .source(PROMPT).target(RESPONSES).action(setRoundPromptAction).and()
                .withExternal()
                    .source(RESPONSES).target(RESPONSES_CHOICE).event(RESPOND).and()
                .withChoice()
                    .source(RESPONSES_CHOICE).first(REWARD, isAllPlayersRespondedGuard, addPlayerResponseAction).last(RESPONSES, addPlayerResponseAction).and()
                .withExternal()
                    .source(REWARD).target(WINNER).action(incrementScoreAction).event(PICK_WINNER).and()
                .withChoice()
                    .source(WINNER).first(END_GAME, new IsEndGameGuard()).last(DEAL, resetGameCardsAction);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<GameState, GameEvent> configurer) throws Exception {
        StateMachineListenerAdapter<GameState, GameEvent> adapter = new StateMachineListenerAdapter<>() {
            @Override
            public void stateChanged(State<GameState, GameEvent> from, State<GameState, GameEvent> to) {
                log.info("State changed from {} to {}", from, to);
            }
        };
        configurer.withConfiguration().listener(adapter);
    }
}
