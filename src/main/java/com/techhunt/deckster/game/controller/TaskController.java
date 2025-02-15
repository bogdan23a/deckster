package com.techhunt.deckster.game.controller;

import com.techhunt.deckster.game.entity.GameTask;
import com.techhunt.deckster.game.enums.GameState;
import com.techhunt.deckster.game.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @GetMapping("/{gameState}")
    public ResponseEntity<GameTask> getTaskByGameState(@RequestHeader("game_id") UUID gameId, @PathVariable GameState gameState) {
        return ResponseEntity.ok(service.getTask(gameId, gameState));
    }
}
