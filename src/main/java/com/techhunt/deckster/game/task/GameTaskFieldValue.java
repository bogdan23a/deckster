package com.techhunt.deckster.game.task;

import lombok.Data;

import java.util.List;

@Data
public abstract class GameTaskFieldValue {
    private final InputType inputType;
    private final List<?> values;
}
