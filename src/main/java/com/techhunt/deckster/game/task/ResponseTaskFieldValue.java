package com.techhunt.deckster.game.task;

import java.util.List;

public class ResponseTaskFieldValue extends GameTaskFieldValue {

    public ResponseTaskFieldValue() {
        super();
    }

    public ResponseTaskFieldValue(InputType inputType, List<?> cards) {
        super(inputType, cards);
    }
}
