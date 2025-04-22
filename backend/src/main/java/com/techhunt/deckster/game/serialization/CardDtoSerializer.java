package com.techhunt.deckster.game.serialization;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.techhunt.deckster.game.entity.CardDto;
import com.techhunt.deckster.game.entity.CardType;
import com.techhunt.deckster.game.service.CardTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class CardDtoSerializer extends StdDeserializer<CardDto> {

    @Autowired
    private CardTypeService cardTypeService;

    public CardDtoSerializer() {
        this(null);
    }

    protected CardDtoSerializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public CardDto deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);
        JsonNode idNode = node.get("id");
        JsonNode contentNode = node.get("content");
        JsonNode deckIdNode = node.get("deckId");
        JsonNode typeIdNode = node.get("type");
        JsonNode promptsNode = node.get("prompts");
        JsonNode responseGroupNode = node.get("responseGroup");
        CardType type = cardTypeService.findById(UUID.fromString(typeIdNode.asText()));
        return new CardDto(
                UUID.fromString(idNode.asText()),
                contentNode.asText(),
                UUID.fromString(deckIdNode.asText()),
                type,
                promptsNode.asInt(),
                UUID.fromString(responseGroupNode.asText()));
    }
}
