package com.techhunt.deckster.game.task;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameTaskDetail {

    private int order;
    private GameTaskFieldLabel label;
    private GameTaskInput input;
}
