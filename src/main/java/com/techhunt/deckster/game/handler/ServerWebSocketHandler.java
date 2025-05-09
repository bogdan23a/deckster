//package com.techhunt.deckster.game.handler;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.SubProtocolCapable;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//import org.springframework.web.util.HtmlUtils;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//@Slf4j
//public class ServerWebSocketHandler extends TextWebSocketHandler implements SubProtocolCapable {
//
//    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        log.info("Server connection established");
//        sessions.add(session);
//        TextMessage message = new TextMessage("Hello from server");
//        log.info("Server sends: {}", message);
//        session.sendMessage(message);
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
//        log.info("Server connection closed");
//        sessions.remove(session);
//    }
//
//    @Scheduled(fixedRate = 5000)
//    void sendPeriodicMessage() throws IOException {
//        for (WebSocketSession session : sessions) {
//            if (session.isOpen()) {
//                TextMessage message = new TextMessage("Periodic message from server");
//                log.info("Server sends: {}", message);
//                session.sendMessage(message);
//            }
//        }
//    }
//
//    @Override
//    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
//        String request = message.getPayload();
//        log.info("Server received: {}", request);
//        String response = String.format("Server received: %s", HtmlUtils.htmlEscape(request));
//        log.info("Server responds: {}", response);
//        session.sendMessage(new TextMessage(response));
//    }
//
//    @Override
//    public void handleTransportError(WebSocketSession session, Throwable exception) {
//        log.error("Server transport error", exception);
//    }
//
//    @Override
//    public List<String> getSubProtocols() {
//        return List.of("subprotocol.game.websocket");
//    }
//}
