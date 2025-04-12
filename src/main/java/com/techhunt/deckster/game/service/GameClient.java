package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.Card;
import com.techhunt.deckster.game.entity.Game;
import com.techhunt.deckster.game.entity.GameCard;
import com.techhunt.deckster.game.entity.Player;
import com.techhunt.deckster.game.repository.GameRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.techhunt.deckster.game.enums.GameState.DRAFT;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameClient implements GameService {

    public static final String GAME_ID_HEADER = "game_id";
    public static final String EMAIL_HEADER = "email";
    public static final String DECK_ID_HEADER = "deck_id";
    public static final String CARD_IDS_HEADER = "card_ids";

    private final GameRepository repository;
    private final PlayerService playerService;
    private final DeckService deckService;
    private final GameCardService gameCardService;
    private final CardService cardService;
    private final CardTypeService cardTypeService;

    @Override
    public List<Game> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Game newGame(String email) {
        Game game = repository.save(new Game(DRAFT));
        Player player = new Player(email, game.getId(), true);
        player.setHost(true);
        playerService.save(player);
        return game;
    }

    @Override
    public Game findById(UUID gameId) {
        return repository.findById(gameId).orElse(new Game());
    }

    @Override
    @Transactional
    public void save(Game game) {
        repository.save(game);
    }

    @Override
    public void dealCards(UUID gameId) {
        Game game = findById(gameId);
        game.getPlayers().forEach(player -> deckService.dealHand(player, game.getDeck()));
    }

    @Override
    public void choosePrompt(UUID gameId, String email) {
        Game game = findById(gameId);
        List<GameCard> gameCards = gameCardService.findByGameId(gameId);
        Set<GameCard> usedCards = gameCards.stream().filter(GameCard::isUsed).collect(Collectors.toSet());
        List<Card> unusedPrompts = game.getDeck().getCards().stream()
                .filter(card -> Objects.equals(card.getType().getName(), "Prompt"))
                .filter(card -> usedCards.stream()
                        .map(GameCard::getCardId)
                        .noneMatch(cardId -> Objects.equals(cardId, card.getId())))
                .collect(Collectors.toList());
        Collections.shuffle(unusedPrompts);
        GameCard prompt = unusedPrompts.stream().findFirst()
                .map(card -> new GameCard(gameId, card.getId(), email, true, Timestamp.from(Instant.now())))
                .orElse(null);
        gameCardService.save(prompt);
    }

    @Override
    public Card getPrompt(UUID gameId) {
        List<GameCard> gameCards = gameCardService.findByGameId(gameId);
        List<GameCard> promptGameCards = gameCards.stream()
                .filter(gameCard -> {
                    Card card = cardService.findById(gameCard.getCardId());
                    return "Prompt".equals(card.getType().getName());
                })
                .sorted(Comparator.comparing(GameCard::getUsedAt))
                .collect(Collectors.toList());
        Collections.reverse(promptGameCards);
        Card prompt = promptGameCards.stream()
                .map(GameCard::getCardId)
                .map(cardService::findById)
                .filter(card -> Objects.equals(card.getType().getName(), "Prompt"))
                .findFirst().orElse(null);
        if (prompt == null) {
            log.error("Prompt card not found for game {}", gameId);
            return null;
        }
        return prompt;
    }

    @Override
    public void removeUsedCardsFromUsersHands(UUID gameId) {
        List<GameCard> usedCards = gameCardService.findByGameId(gameId).stream().filter(GameCard::isUsed).toList();
        usedCards.stream().peek(card -> card.setEmail(null)).forEach(gameCardService::save);
    }
}