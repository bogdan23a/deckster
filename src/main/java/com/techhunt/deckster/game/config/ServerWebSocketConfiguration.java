package com.techhunt.deckster.game.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class ServerWebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    public ServerWebSocketConfiguration() {
    }

//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(webSocketHandler(),"/websocket").setAllowedOriginPatterns("*");
//                .setWebSocketEnabled(true).setSessionCookieNeeded(false);
//    }
//
//    @Bean
//    public WebSocketHandler webSocketHandler() {
//        return new ServerWebSocketHandler();
//    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket").setAllowedOriginPatterns("*");
    }

    @Override
    public void configureMessageBroker(org.springframework.messaging.simp.config.MessageBrokerRegistry config) {
        config.enableSimpleBroker("");
    }

}