package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.Card;
import com.techhunt.deckster.game.entity.Game;
import com.techhunt.deckster.game.entity.GameCard;
import com.techhunt.deckster.game.entity.Player;
import com.techhunt.deckster.game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    private final GameRepository repository;
    private final PlayerService playerService;
    private final DeckService deckService;
    private final GameCardService gameCardService;
    private final CardService cardService;

    @Override
    public List<Game> findAll() {
        return repository.findAll();
    }

    @Override
    public Game newGame(String email) {
        Game game = repository.save(new Game(DRAFT));
        playerService.save(new Player(email, game.getId(), true));
        return game;
    }

    @Override
    public Game findById(UUID gameId) {
        return repository.findById(gameId).orElse(new Game());
    }

    @Override
    public void save(Game game) {
        repository.save(game);
    }

    @Override
    public void dealCards(UUID gameId) {
        Game game = findById(gameId);
        game.getPlayers().forEach(player -> deckService.dealHand(player, game.getDeck()));
    }

    @Override
    public void choosePrompt(UUID gameId) {
        Game game = findById(gameId);
        List<GameCard> gameCards = gameCardService.findByGameId(gameId);
        Set<UUID> usedCards = gameCards.stream().map(GameCard::getCardId).collect(Collectors.toSet());
        GameCard prompt = game.getDeck().getCards().stream()
                .filter(card -> Objects.equals(card.getType().getName(), "Prompt"))
                .filter(card -> !usedCards.contains(card.getId()))
                .findFirst()
                .map(card -> new GameCard(gameId, card.getId()))
                .orElse(null);
        gameCardService.save(prompt);
    }

    @Override
    public Card getPrompt(UUID gameId) {
        List<Card> promptGameCards = gameCardService.findByGameId(gameId).stream()
                .map(GameCard::getCardId)
                .map(cardService::findById)
                .filter(card -> Objects.equals(card.getType().getName(), "Prompt"))
                .toList();
        if (promptGameCards == null || promptGameCards.size() != 1) {
            log.error("Prompt card not found for game {}", gameId);
            return null;
        }
        return promptGameCards.getFirst();
    }
}