package com.techhunt.deckster.game.task;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class SetupTaskFieldValue extends GameTaskFieldValue {

    public SetupTaskFieldValue(InputType inputType, List<?> values) {
        super(inputType, values);
    }
}
