package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.CardType;

import java.util.List;

public interface CardTypeService {
    
    List<CardType> findAll();
    List<String> getAllNames();
    CardType save(CardType type);

    CardType getId(String typeName);
    CardType findByName(String name);
}
