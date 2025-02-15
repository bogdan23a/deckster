package com.techhunt.deckster.game.controller;

import com.techhunt.deckster.game.entity.Game;
import com.techhunt.deckster.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService service;

//    @GetMapping
//    public ResponseEntity<List<Game>> findAll() {
//        return ResponseEntity.ok(service.findAll());
//    }

    @GetMapping
    public ResponseEntity<Game> findById(@RequestHeader("game_id") UUID gameId) {
        return ResponseEntity.ok(service.findById(gameId));
    }

    @PutMapping
    public ResponseEntity<Game> createNewGame(@RequestHeader String email) {
        return ResponseEntity.ok(service.newGame(email));
    }

    @PostMapping("/{gameId}/{event}")
    public void get(@PathVariable String gameId, @PathVariable String event) {
        service.sendEvent(event, gameId);
    }
}
