package com.techhunt.deckster.game.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.techhunt.deckster.game.serialization.CardDtoSerializer;

import java.util.UUID;

@JsonDeserialize(using = CardDtoSerializer.class)
public record CardDto(
        UUID id,
        String content,
        UUID deckId,
        CardType type,
        int prompts,
        UUID responseGroup
) {
}
