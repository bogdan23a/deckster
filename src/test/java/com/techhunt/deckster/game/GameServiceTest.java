package com.techhunt.deckster.game;

import com.techhunt.deckster.game.entity.Game;
import com.techhunt.deckster.game.repository.GameRepository;
import com.techhunt.deckster.game.service.GameService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GameServiceTest {

    @Autowired
    GameService gameService;

    @Autowired
    GameRepository gameRepository;

    Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    @Transactional
    void sendEventTest() {
//        Game savedGame = gameService.newGame();
//
//        gameService.sendEvent("CREATE", savedGame.getId().toString());
//
//        Game startedGame = gameRepository.getReferenceById(savedGame.getId());
//
//        System.out.println(startedGame);
    }
}
