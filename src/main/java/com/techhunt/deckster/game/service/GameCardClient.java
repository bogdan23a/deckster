package com.techhunt.deckster.game.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techhunt.deckster.game.entity.Card;
import com.techhunt.deckster.game.entity.CardDto;
import com.techhunt.deckster.game.entity.GameCard;
import com.techhunt.deckster.game.repository.GameCardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameCardClient implements GameCardService {

    private final GameCardRepository repository;
    private final ObjectMapper objectMapper;

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
    public Map<String, List<CardDto>> findbyGameIdAndCardTypeAndUsed(UUID gameId, UUID cardType, boolean used) {
        List<Object[]> result = repository.findByGameIdAndCardTypeAndUsedAndEmailNotNullGroupByEmail(gameId, cardType, used);
        return result.stream()
                .collect(Collectors.toMap(
                        obj -> (String) obj[0],
                        obj -> {
                            try {
                                return List.of(objectMapper.readValue(obj[1].toString(), new TypeReference<>() {
                                }));
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        }));
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

    @Override
    public GameCard findOneByGameIdAndResponseGroup(UUID gameId, UUID responseGroup) {
        return repository.findByGameIdAndResponseGroup(gameId, responseGroup).stream().findFirst().orElse(null);
    }
}
