package com.techhunt.deckster.game.task;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GameTaskInput {
    protected InputType inputType;
    protected List<?> values;
}
