//package com.codeup.autoconnect.config;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.WebSocketSession;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Component
//public class WebSocketSessionRegistry {
//    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
//
//    public void addSession(WebSocketSession session) {
//        sessions.put(session.getId(), session);
//    }
//
//    public void removeSession(WebSocketSession session) {
//        sessions.remove(session.getId());
//    }
//
//    public Iterable<WebSocketSession> getActiveSessions() {
//        return sessions.values();
//    }
//}
