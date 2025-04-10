package com.techhunt.deckster.game.service;

import com.techhunt.deckster.game.entity.Card;
import com.techhunt.deckster.game.entity.CardType;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardGenerationClient implements CardGenerationService {

    private final CardTypeService cardTypeService;
    private final ResourceLoader loader;

    @Override
    public Set<Card> generate(String filePath) {
        Map<String, List<String>> cards = getCards(filePath);
        cards.keySet().stream()
                .map(CardType::new)
                .forEach(cardTypeService::save);
        return cards.entrySet().stream()
                .flatMap(entry -> {
                    if (entry.getKey().isEmpty() || entry.getValue().isEmpty()) {
                        return null;
                    }
                    CardType type = cardTypeService.getByName(entry.getKey());
                    return entry.getValue().stream()
                            .map(content -> new Card(content, type, getPrompts(content, type)));
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private int getPrompts(String content, CardType type) {
        if (!Objects.equals(type.getName(), "Prompt")) {
            return 0;
        }
        Pattern pattern = Pattern.compile("______");
        Matcher matcher = pattern.matcher(content);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count > 0 ? count : 1;
    }

    private Map<String, List<String>> getCards(String filePath) {
        Map<String, List<String>> cards = new HashMap<>();
        Resource resource = loader.getResource("classpath:" + File.separator + filePath);
        if (!resource.exists()) {
            throw new IllegalArgumentException("File not found: " + filePath);
        }
        try (Scanner scanner = new Scanner(resource.getFile())) {
            while (scanner.hasNextLine()) {
                String[] entries = scanner.nextLine().split(",");
                if (entries.length < 2) {
                    continue;
                }
                String content = String.join(",", Arrays.copyOfRange(entries, 1, entries.length));
                if (cards.containsKey(entries[0])) {
                    cards.get(entries[0]).add(content);
                } else {
                    List<String> contentList = new ArrayList<>();
                    contentList.add(content);
                    cards.put(entries[0], contentList);
                }
            }
            return cards;
        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading file: " + filePath, e);
        }
    }
}
