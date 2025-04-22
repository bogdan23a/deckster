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
    WINNER("Choose The Winner"),
    HOST_SETUP("The game is being set up"),
    OTHER_PLAYERS_TURN("Waiting for other players to finish their turn"),
    SCORE("Score"),
    SHARE_GAME("Share this game with your friends");

    private final String message;

    public String toString() {
        return message;
    }
}
