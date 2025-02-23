package com.techhunt.deckster.game.task;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameTaskFieldLabel {
    DECK("Choose Your Deck"),
    PLAYERS("Table"),
    PROMPT(""),
    RESPONSES("Choose The Response"),
    WINNER("Choose The Winner");

    private final String message;
}
