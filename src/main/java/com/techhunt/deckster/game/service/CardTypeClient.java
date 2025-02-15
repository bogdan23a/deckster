package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.CardType;
import com.techhunt.deckster.game.repository.CardTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public CardType getId(String typeName) {
        return repository.findByName(typeName);
    }

}
