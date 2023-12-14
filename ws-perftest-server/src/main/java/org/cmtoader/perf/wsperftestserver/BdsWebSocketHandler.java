package org.cmtoader.perf.wsperftestserver;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class BdsWebSocketHandler extends TextWebSocketHandler {

    private final WebSocketDistributorService webSocketDistributorService;

    public BdsWebSocketHandler(WebSocketDistributorService webSocketDistributorService) {
        this.webSocketDistributorService = webSocketDistributorService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        this.webSocketDistributorService.registerBdsWebSocketSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        this.webSocketDistributorService.deregisterBsdWebSocketSession(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        this.webSocketDistributorService.handleBdsTextMessage(message);
    }
}
