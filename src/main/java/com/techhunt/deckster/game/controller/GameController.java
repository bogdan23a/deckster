package com.techhunt.deckster.game.controller;

import com.techhunt.deckster.game.entity.Game;
import com.techhunt.deckster.game.enums.GameEvent;
import com.techhunt.deckster.game.service.GameService;
import com.techhunt.deckster.game.service.GameStateMachineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService service;
    private final GameStateMachineService gameStateMachineService;

    @GetMapping
    public ResponseEntity<Game> findById(@RequestHeader("game_id") UUID gameId) {
        try {
            return ResponseEntity.ok(service.findById(gameId));
        } catch (Exception e) {
            log.error("Something went wrong2", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<Game> createNewGame(@RequestHeader String email) {
        return ResponseEntity.ok(service.newGame(email));
    }

    @PostMapping("/{event}")
    public ResponseEntity<Game> sendEvent(@PathVariable GameEvent event, @RequestBody Map<String, String> message) {
        UUID gameId = UUID.fromString(message.get("game_id"));
        log.info("Received event {} for game {}", event, gameId);
        try {
            gameStateMachineService.sendEvent(event, message);
            Game game = service.findById(gameId);
            return ResponseEntity.ok(game);
        } catch (Exception e) {
            log.error("Something went wrong", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
