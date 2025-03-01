package com.techhunt.deckster.game.task;

import com.techhunt.deckster.game.entity.Card;

import java.util.List;

public class RewardTaskFieldValue extends GameTaskFieldValue {
    public RewardTaskFieldValue() {
        super();
    }

    public RewardTaskFieldValue(InputType inputType, List<Card> responses) {
        super(inputType, responses);
    }
}
