package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.Card;
import com.techhunt.deckster.game.entity.CardType;

import java.util.List;
import java.util.UUID;

public interface CardService {
    List<Card> findAll();
    void save(Card card);
    List<Card> findByDeckId(UUID deckId);
    List<Card> findByEmailAndCardType(String email, CardType cardType);
    Card findOneByCardType(CardType cardType);
    Card findById(UUID cardId);
}
