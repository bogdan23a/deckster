package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.Player;

import java.util.List;
import java.util.UUID;

public interface PlayerService {
    List<Player> findByGameId(UUID gameId);
    Player findByEmail(String email);
    Player save(Player player);
    int countByGameId(UUID gameId);
    List<String> findIdsByGameId(UUID gameId);
}
