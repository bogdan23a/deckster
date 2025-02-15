package com.techhunt.deckster.game.repository;

import com.techhunt.deckster.game.entity.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeckRepository extends JpaRepository<Deck, UUID> {
    boolean existsByName(String name);
    Deck findByName(String name);

    @Query("SELECT name FROM Deck")
    List<String> findNames();
}