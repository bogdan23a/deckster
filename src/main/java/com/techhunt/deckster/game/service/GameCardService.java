package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.Card;
import com.techhunt.deckster.game.entity.GameCard;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface GameCardService {
    List<GameCard> findByGameId(UUID gameId);
    void save(GameCard card);
    List<GameCard> findByCardId(UUID cardId);
    List<GameCard> findByCardIdNotIn(List<UUID> cardIds);
    GameCard findByGameIdAndCardId(UUID gameId, UUID cardId);
    int countByGameIdAndType(UUID gameId, UUID typeId);
    Map<String, List<Card>> findbyGameIdAndCardTypeAndUsed(UUID gameId, UUID cardType, boolean isUsed);
    void deleteByGameId(UUID gameId);
    void removeAll(Set<GameCard> hand);
}
