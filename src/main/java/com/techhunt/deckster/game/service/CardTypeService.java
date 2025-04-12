package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.CardType;

import java.util.List;
import java.util.UUID;

public interface CardTypeService {
    
    List<CardType> findAll();
    List<String> getAllNames();
    CardType save(CardType type);

    CardType getByName(String typeName);
    CardType findByName(String name);
    CardType findById(UUID typeId);
}
