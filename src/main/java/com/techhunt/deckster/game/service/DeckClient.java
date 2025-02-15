package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.Card;
import com.techhunt.deckster.game.entity.Deck;
import com.techhunt.deckster.game.repository.DeckRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeckClient implements DeckService {

    private final DeckRepository repository;
    private final CardGenerationService cardGenerationService;
    private final CardService cardService;

    @Override
    public List<Deck> findAll() {
        return repository.findAll();
    }

    @Override
    public Deck findById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Deck save(String filePath) {
        Set<Card> cards = cardGenerationService.generate(filePath);
        Deck deck = save(new Deck(filePath.split("\\.")[0], cards));
        cards.stream()
                .peek(card -> card.setDeckId(deck.getId()))
                .forEach(cardService::save);
        return deck;
    }

    @Override
    @Transactional
    public Deck save(Deck deck) {
        if (repository.existsByName(deck.getName())) {
            return repository.findByName(deck.getName());
        }
        return repository.save(deck);
    }

    @Override
    public void delete(UUID deckId) {
        repository.deleteById(deckId);
    }

    @Override
    public List<String> findAllNames() {
        return repository.findNames();
    }
    
}
