package com.FitMeet.config;

import com.FitMeet.model.ChatMessage;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Kết nối WebSocket đã được thiết lập.");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String messagePayload = message.getPayload();
        System.out.println("Nhận tin nhắn: " + messagePayload);

        // Kiểm tra nếu tin nhắn có vẻ là một đối tượng JSON hợp lệ
        if (isJson(messagePayload)) {
            try {
                // Chuyển đổi tin nhắn nhận được thành đối tượng ChatMessage
                ChatMessage chatMessage = new Gson().fromJson(messagePayload, ChatMessage.class);

                // Gửi tin nhắn lại cho client (Android)
                sendMessage(session, chatMessage);
            } catch (Exception e) {
                System.err.println("Lỗi khi chuyển đổi JSON: " + e.getMessage());
            }
        } else {
            System.out.println("Dữ liệu không phải là đối tượng JSON hợp lệ, bỏ qua.");
        }
    }

    // Phương thức kiểm tra xem chuỗi có phải là JSON hợp lệ không
    private boolean isJson(String message) {
        try {
            new JsonParser().parse(message);
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        }
    }


    private void sendMessage(WebSocketSession session, ChatMessage chatMessage) throws
            IOException {
        // Chuyển đối tượng ChatMessage thành JSON và gửi lại cho client
        String messagePayload = new Gson().toJson(chatMessage);
        session.sendMessage(new TextMessage(messagePayload));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Kết nối WebSocket đã bị đóng.");
    }
}