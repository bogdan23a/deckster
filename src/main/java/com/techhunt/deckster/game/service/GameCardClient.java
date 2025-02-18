package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.GameCard;
import com.techhunt.deckster.game.repository.GameCardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<GameCard> findByCardIdNotIn(List<UUID> cardIds) {
        return repository.findByCardIdNotIn(cardIds);
    }
}
