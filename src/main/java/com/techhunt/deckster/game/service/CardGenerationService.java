package com.techhunt.deckster.game.service;


import com.techhunt.deckster.game.entity.Card;

import java.util.Set;

public interface CardGenerationService {

    Set<Card> generate(String filePath);
}
