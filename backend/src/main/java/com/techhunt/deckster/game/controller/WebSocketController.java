package com.techhunt.deckster.game.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class WebSocketController {

    @MessageMapping("/sendMessage")
    @SendTo("/public")
    public String send(@Payload String message) {
        log.info("Received message: {}", message);
        return message;
    }
}
