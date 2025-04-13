package com.techhunt.deckster.game.repository;

import com.techhunt.deckster.game.entity.Card;
import com.techhunt.deckster.game.entity.GameCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface GameCardRepository extends JpaRepository<GameCard, UUID> {
    List<GameCard> findByGameId(UUID gameId);
    List<GameCard> findByCardId(UUID cardId);
    List<GameCard> findByCardIdNotIn(Collection<UUID> cardIds);

    GameCard findByGameIdAndCardId(UUID gameId, UUID cardId);

    int countByGameId(UUID gameId);

    @Query("SELECT COUNT(*) FROM GameCard AS gc JOIN Card AS c ON gc.cardId=c.id WHERE gc.gameId = :gameId AND c.type.id = :typeId AND gc.email IS NOT NULL")
    int countByGameIdAndType(UUID gameId, UUID typeId);

    @Query("SELECT " +
            "gc.email, " +
            "JSON_AGG(JSON_BUILD_OBJECT(" +
                "'id', c.id, " +
                "'content', c.content, " +
                "'deckId', c.deckId, " +
                "'type', c.type, " +
                "'prompts', c.prompts, " +
                "'responseGroup', gc.responseGroup )) AS cards " +
            "FROM GameCard AS gc " +
            "JOIN Card AS c " +
            "ON gc.cardId=c.id " +
            "WHERE " +
                "gc.gameId = :gameId " +
                "AND c.type.id = :cardType " +
                "AND gc.used = :used " +
                "AND gc.email IS NOT NULL " +
            "GROUP BY gc.email")
    List<Object[]> findByGameIdAndCardTypeAndUsedAndEmailNotNullGroupByEmail(UUID gameId, UUID cardType, boolean used);

    void removeByGameId(UUID gameId);

    GameCard findOneByGameIdAndResponseGroup(UUID gameId, UUID responseGroup);

    List<GameCard> findByGameIdAndResponseGroup(UUID gameId, UUID responseGroup);

    @Query("SELECT " +
                "COUNT(DISTINCT gc.responseGroup) " +
            "FROM GameCard AS gc " +
            "WHERE " +
                "gc.gameId = :gameId " +
                "AND gc.cardId IN (SELECT c.id FROM Card AS c WHERE c.type.id = :cardType)")
    int countResponseGroupsByGameIdAndType(UUID gameId, UUID cardType);

    @Query("SELECT gc " +
            "FROM GameCard gc " +
            "WHERE " +
                "gc.email = :email " +
                "AND gc.cardId IN (SELECT c.id FROM Card c WHERE c.type.name = :cardType) " +
                "AND gc.used = :used")
    List<GameCard> findByEmailAndCardTypeAndUsed(String email, String cardType, boolean used);
}
