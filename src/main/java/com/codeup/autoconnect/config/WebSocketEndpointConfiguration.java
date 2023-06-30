package com.codeup.autoconnect.config;

import com.codeup.autoconnect.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketEndpointConfiguration implements WebSocketConfigurer {
    private final WebSocketMessageHandler webSocketMessageHandler;

    @Autowired
    public WebSocketEndpointConfiguration(WebSocketMessageHandler webSocketMessageHandler) {
        this.webSocketMessageHandler = webSocketMessageHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketMessageHandler, "/messages/websocket")
                .setAllowedOrigins("*");
    }

    @Bean
    public WebSocketSessionRegistry webSocketSessionRegistry() {
        return new WebSocketSessionRegistry();
    }
}

