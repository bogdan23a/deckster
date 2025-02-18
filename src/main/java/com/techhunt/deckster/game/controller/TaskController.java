package com.techhunt.deckster.game.controller;

import com.techhunt.deckster.game.entity.GameTask;
import com.techhunt.deckster.game.enums.GameState;
import com.techhunt.deckster.game.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @PostMapping("/{gameState}")
    public ResponseEntity<GameTask> getTaskByGameState(@PathVariable GameState gameState, @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(service.getTask(gameState, body));
    }
}
