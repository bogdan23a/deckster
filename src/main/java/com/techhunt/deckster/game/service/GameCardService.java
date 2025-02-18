package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.GameCard;

import java.util.List;
import java.util.UUID;

public interface GameCardService {
    List<GameCard> findByGameId(UUID gameId);
    void save(GameCard card);
    List<GameCard> findByCardId(UUID cardId);
    List<GameCard> findByCardIdNotIn(List<UUID> cardIds);
}
