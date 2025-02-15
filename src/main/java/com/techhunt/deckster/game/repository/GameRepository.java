package com.techhunt.deckster.game.repository;

import com.techhunt.deckster.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {
}
