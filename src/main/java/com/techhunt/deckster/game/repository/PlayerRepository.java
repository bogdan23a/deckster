package com.techhunt.deckster.game.repository;

import com.techhunt.deckster.game.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, UUID> {
    List<Player> findByGameId(UUID gameId);

    Player findByEmail(String email);

    int countByGameId(UUID gameId);

    @Query("SELECT p.email FROM Player AS p WHERE p.gameId = :gameId")
    List<String> getEmailsByGameId(UUID gameId);
}
