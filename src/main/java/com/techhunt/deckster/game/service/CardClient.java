package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.Card;
import com.techhunt.deckster.game.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardClient implements CardService {

    private final CardRepository repository;

    @Override
    public List<Card> findAll() {
        return repository.findAll();
    }

    @Override
    public void save(Card card) {
        repository.save(card);
    }

    @Override
    public List<Card> findByDeckId(UUID deckId) {
        return repository.findByDeckId(deckId);
    }
}
