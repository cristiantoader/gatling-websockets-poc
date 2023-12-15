package org.cmtoader.perf.wsperftestserver;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class WebSocketDistributorService {

    private final CopyOnWriteArrayList<WebSocketSession> guiWebSocketSessions = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<WebSocketSession> bdsWebSocketSessions = new CopyOnWriteArrayList<>();

    public void registerGuiWebSocketSession(WebSocketSession session) {
        guiWebSocketSessions.add(session);
    }

    public void registerBdsWebSocketSession(WebSocketSession session) {
        bdsWebSocketSessions.add(session);
    }

    public void deregisterBsdWebSocketSession(WebSocketSession session) {
        bdsWebSocketSessions.remove(session);
    }

    public void deregisterGuiWebSocketSession(WebSocketSession session) {
        this.guiWebSocketSessions.remove(session);
    }

    public void handleBdsTextMessage(TextMessage message) {
//        log.info("{}", message);

//        Try.run(() -> Thread.sleep((int) Math.floor(new Random().nextGaussian(500, 5))))
//           .onFailure(err -> log.error("Failed to sleep for message.", err));

//        Try.run(() -> Thread.sleep(250))
//                .onFailure(err -> log.error("Failed to sleep for message.", err));

        this.guiWebSocketSessions.parallelStream()
                                 .forEach(guiSocket -> Try.run(() -> guiSocket.sendMessage(message))
                                                          .onFailure(err -> log.error("Failed to send message to {}.", guiSocket, err)));
    }
}
