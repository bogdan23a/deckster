package com.techhunt.deckster.game.task;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameTaskFieldLabel {
    DECK("Choose Your Deck"),
    PLAYERS("Table");

    private final String message;
}
