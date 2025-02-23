package com.techhunt.deckster.game.repository;

import com.techhunt.deckster.game.entity.Card;
import com.techhunt.deckster.game.entity.GameCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface GameCardRepository extends JpaRepository<GameCard, UUID> {
    List<GameCard> findByGameId(UUID gameId);
    List<GameCard> findByCardId(UUID cardId);
    List<GameCard> findByCardIdNotIn(Collection<UUID> cardIds);

    GameCard findByGameIdAndCardId(UUID gameId, UUID cardId);

    int countByGameId(UUID gameId);

    @Query("SELECT COUNT(*) FROM GameCard AS gc JOIN Card AS c ON gc.cardId=c.id WHERE gc.gameId = :gameId AND c.type.id = :typeId")
    int countByGameIdAndType(UUID gameId, UUID typeId);

    @Query("SELECT c FROM GameCard AS gc JOIN Card AS c ON gc.cardId=c.id WHERE gc.gameId = :gameId AND c.type.id = :cardType AND gc.used = :used AND gc.email IS NOT NULL")
    List<Card> findByGameIdAndCardTypeAndUsedAndEmailNotNull(UUID gameId, UUID cardType, boolean used);

    void removeByGameId(UUID gameId);
}
