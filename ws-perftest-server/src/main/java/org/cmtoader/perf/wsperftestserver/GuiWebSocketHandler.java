package org.cmtoader.perf.wsperftestserver;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class GuiWebSocketHandler extends TextWebSocketHandler {

    private final WebSocketDistributorService webSocketDistributorService;

    public GuiWebSocketHandler(WebSocketDistributorService webSocketDistributorService) {
        this.webSocketDistributorService = webSocketDistributorService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        this.webSocketDistributorService.registerGuiWebSocketSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        this.webSocketDistributorService.deregisterGuiWebSocketSession(session);
    }
}
