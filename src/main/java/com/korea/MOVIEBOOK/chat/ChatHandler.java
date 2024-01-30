package com.korea.MOVIEBOOK.chat;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ChatHandler extends TextWebSocketHandler {
    private final List<WebSocketSession> sessions = new ArrayList<>();


    @Override
    // When WebSocket Connected
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        updateParticipantCount();
    }

    @Override
    // When Message Sent
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        for (WebSocketSession connected: sessions) {
            connected.sendMessage(message);
        }
    }

    @Override
    // When WebSocket Connect Terminated
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        updateParticipantCount();
    }

    private void updateParticipantCount() {
        int count = sessions.size();
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage("{\"participantCount\": " + count + "}"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}