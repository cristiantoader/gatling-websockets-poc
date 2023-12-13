package org.cmtoader.perf.wsperftestserver;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final GuiWebSocketHandler guiWebSocketHandler;
    private final BdsWebSocketHandler bdsWebSocketHandler;

    public WebSocketConfig(GuiWebSocketHandler guiWebSocketHandler, BdsWebSocketHandler bdsWebSocketHandler) {
        this.guiWebSocketHandler = guiWebSocketHandler;
        this.bdsWebSocketHandler = bdsWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(this.guiWebSocketHandler, "/websocket/gui-register");
        registry.addHandler(this.bdsWebSocketHandler, "/websocket/bds-register");
    }
}
