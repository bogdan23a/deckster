package com.techhunt.deckster.game.controller;

import com.techhunt.deckster.game.entity.Deck;
import com.techhunt.deckster.game.service.DeckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/deck")
@RequiredArgsConstructor
public class DeckController {

    @Autowired
    private DeckService service;

    @GetMapping
    public ResponseEntity<List<Deck>> findAll() {
        log.debug("Find all decks");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Deck> findById(@PathVariable UUID id) {
        log.debug("Find {}", id);
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/{filePath}")
    public ResponseEntity<Deck> generate(@PathVariable String filePath) {
        try {
            return ResponseEntity.ok(service.save(filePath));
        } catch (Exception e) {
            log.error("Could not generate deck", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
