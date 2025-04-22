package com.techhunt.deckster.game.controller;

import com.techhunt.deckster.game.entity.Card;
import com.techhunt.deckster.game.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {

    private final CardService service;

    @GetMapping
    public ResponseEntity<List<Card>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{deckId}")
    public ResponseEntity<List<Card>> findByDeckId(@PathVariable UUID deckId) {
        return ResponseEntity.ok(service.findByDeckId(deckId));
    }
}
