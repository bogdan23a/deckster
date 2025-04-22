package com.techhunt.deckster.game.controller;

import com.techhunt.deckster.game.entity.Player;
import com.techhunt.deckster.game.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService service;

    @GetMapping("/{id}")
    public ResponseEntity<List<Player>> get(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findByGameId(id));
    }
}
