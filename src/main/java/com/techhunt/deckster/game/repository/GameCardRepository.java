package com.techhunt.deckster.game.repository;

import com.techhunt.deckster.game.entity.GameCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface GameCardRepository extends JpaRepository<GameCard, UUID> {
    List<GameCard> findByGameId(UUID gameId);
    List<GameCard> findByCardId(UUID cardId);
    List<GameCard> findByCardIdNotIn(Collection<UUID> cardIds);
}
