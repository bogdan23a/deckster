package com.techhunt.deckster.game.entity;

import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.task.GameTaskFieldValue;

import java.util.List;
import java.util.Map;

public record GameTask(Map<String, GameTaskFieldValue> display, List<GameEvent> events) {

}
