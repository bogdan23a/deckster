package com.techhunt.deckster.game.repository;

import com.techhunt.deckster.game.entity.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CardTypeRepository extends JpaRepository<CardType, UUID> {
    boolean existsByName(String name);
    CardType findByName(String name);
    @Query("SELECT name FROM CardType")
    List<String> findName();
}
