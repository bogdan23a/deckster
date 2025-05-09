package com.techhunt.deckster.game.entity;

import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.task.GameTaskDetail;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GameTask {
    List<GameTaskDetail> details;
//    Map<GameTaskFieldLabel, GameTaskFieldValue> display;
    List<GameEvent> events;
}
