package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.CardType;
import com.techhunt.deckster.game.repository.CardTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CardTypeClient implements CardTypeService {

    private final CardTypeRepository repository;

    public CardTypeClient(CardTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CardType> findAll() {
        return repository.findAll();
    }

    @Override
    public List<String> getAllNames() {
        return repository.findName();
    }

    @Override
    @Transactional
    public CardType save(CardType type) {
        if (repository.existsByName((type.getName()))) {
            return repository.findByName(type.getName());
        }
        return repository.save(new CardType(type.getName()));
    }

    @Override
    public CardType getByName(String typeName) {
        return repository.findByName(typeName);
    }

    @Override
    public CardType findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public CardType findById(UUID typeId) {
        return repository.findById(typeId).orElse(null);
    }

}
