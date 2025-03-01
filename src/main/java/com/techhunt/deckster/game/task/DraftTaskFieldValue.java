package com.techhunt.deckster.game.task;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class DraftTaskFieldValue extends GameTaskFieldValue {

    public DraftTaskFieldValue(InputType inputType, List<?> values) {
        super(inputType, values);
    }
}
