//package com.codeup.autoconnect.config;
//
//import com.codeup.autoconnect.models.Message;
//import com.codeup.autoconnect.services.MessageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.AbstractWebSocketHandler;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Component
//public class WebSocketMessageHandler extends AbstractWebSocketHandler {
//    private final MessageService messageService;
//    private WebSocketSessionRegistry sessionRegistry;
//
//    @Autowired
//    public WebSocketMessageHandler(MessageService messageService) {
//        this.messageService = messageService;
//    }
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String payload = message.getPayload();
//        // Process the incoming message
//        System.out.println("Received message: " + payload);
//
//        // Save the message in the database
//        Message savedMessage = messageService.sendAMessage(new Message(payload));
//        System.out.println("Saved message: " + savedMessage.getId());
//
//        // Send the received message to all connected clients
//        for (WebSocketSession client : getSessionRegistry().getActiveSessions()) {
//            if (client.isOpen()) {
//                client.sendMessage(new TextMessage(payload));
//            }
//        }
//    }
//
//    public void setSessionRegistry(WebSocketSessionRegistry sessionRegistry) {
//        this.sessionRegistry = sessionRegistry;
//    }
//
//    private WebSocketSessionRegistry getSessionRegistry() {
//        return sessionRegistry;
//    }
//
//    @Component
//    public static class WebSocketSessionRegistry {
//        private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
//
//        public void addSession(WebSocketSession session) {
//            sessions.put(session.getId(), session);
//        }
//
//        public void removeSession(WebSocketSession session) {
//            sessions.remove(session.getId());
//        }
//
//        public Iterable<WebSocketSession> getActiveSessions() {
//            return sessions.values();
//        }
//    }
//}
