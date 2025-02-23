package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.Card;
import com.techhunt.deckster.game.entity.Deck;
import com.techhunt.deckster.game.entity.GameCard;
import com.techhunt.deckster.game.entity.Player;
import com.techhunt.deckster.game.repository.DeckRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeckClient implements DeckService {

    private final DeckRepository repository;
    private final CardGenerationService cardGenerationService;
    private final CardService cardService;
    private final PlayerService playerService;
    private final GameCardService gameCardService;

    @Override
    public List<Deck> findAll() {
        return repository.findAll();
    }

    @Override
    public Deck findById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Deck save(String filePath) {
        Set<Card> cards = cardGenerationService.generate(filePath);
        Deck deck = save(new Deck(filePath.split("\\.")[0], cards));
        cards.stream()
                .peek(card -> card.setDeckId(deck.getId()))
                .forEach(cardService::save);
        return deck;
    }

    @Override
    @Transactional
    public Deck save(Deck deck) {
        if (repository.existsByName(deck.getName())) {
            return repository.findByName(deck.getName());
        }
        return repository.save(deck);
    }

    @Override
    public void delete(UUID deckId) {
        repository.deleteById(deckId);
    }

    @Override
    public List<String> findAllNames() {
        return repository.findNames();
    }

    @Override
    public void dealHand(Player player, Deck deck) {
        List<GameCard> usedCards = gameCardService.findByGameId(player.getGameId()).stream().filter(GameCard::isUsed).toList();
        Set<Card> responseCards = deck.getCards().stream()
                .filter(card -> Objects.equals(card.getType().getName(), "Response"))
                .filter(card -> usedCards.stream().noneMatch(gameCard -> Objects.equals(gameCard.getCardId(), card.getId())))
                .collect(Collectors.toSet());
        List<Card> deckCards = new ArrayList<>(responseCards);
        Collections.shuffle(deckCards);
        List<GameCard> hand = deckCards.subList(0, 7).stream().map(card -> new GameCard(player.getGameId(), card.getId())).toList();
        List<GameCard> unusedCards = player.getHand().stream().filter(Predicate.not(GameCard::isUsed)).toList();
        if (unusedCards.isEmpty()) {
            player.setHand(new HashSet<>(hand));
        } else {
            player.getHand().addAll(hand.subList(0, 7 - unusedCards.size()));
        }
        playerService.save(player);
    }
}
