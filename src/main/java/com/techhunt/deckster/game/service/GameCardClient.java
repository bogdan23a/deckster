package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.Card;
import com.techhunt.deckster.game.entity.GameCard;
import com.techhunt.deckster.game.repository.GameCardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameCardClient implements GameCardService {

    private final GameCardRepository repository;

    @Override
    public List<GameCard> findByGameId(UUID gameId) {
        return repository.findByGameId(gameId);
    }

    @Override
    @Transactional
    public void save(GameCard card) {
        repository.save(card);
    }

    @Override
    public List<GameCard> findByCardId(UUID cardId) {
        return List.of();
    }

    @Override
    public List<GameCard> findByCardIdNotIn(List<UUID> cardIds) {
        return repository.findByCardIdNotIn(cardIds);
    }

    @Override
    public GameCard findByGameIdAndCardId(UUID gameId, UUID cardId) {
        return repository.findByGameIdAndCardId(gameId, cardId);
    }

    @Override
    public int countByGameIdAndType(UUID gameId, UUID typeId) {
        return repository.countByGameIdAndType(gameId, typeId);
    }

    @Override
    public List<Card> findbyGameIdAndCardTypeAndUsed(UUID gameId, UUID cardType, boolean used) {
        return repository.findByGameIdAndCardTypeAndUsedAndEmailNotNull(gameId, cardType, used);
    }

    @Override
    @Transactional
    public void deleteByGameId(UUID gameId) {
        repository.removeByGameId(gameId);
    }

    @Override
    public void removeAll(Set<GameCard> hand) {
        repository.deleteAll(hand);
    }
}
