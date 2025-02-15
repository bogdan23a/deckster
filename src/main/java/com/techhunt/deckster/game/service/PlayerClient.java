package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.Player;
import com.techhunt.deckster.game.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayerClient implements PlayerService {

    private final PlayerRepository repository;

    @Override
    public List<Player> findByGameId(UUID gameId) {
        return repository.findByGameId(gameId);
    }

    @Override
    public Player findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Player save(Player player) {
        return repository.save(player);
    }
}
