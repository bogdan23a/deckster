package com.techhunt.deckster.game.repository;

import com.techhunt.deckster.game.entity.Card;
import com.techhunt.deckster.game.entity.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {
    List<Card> findByDeckId(UUID deckId);
    List<Card> findByPlayerEmailAndType(String playerEmail, CardType type);
    List<Card> findByType(CardType type);
}
