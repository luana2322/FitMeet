package com.FitMeet.controller;

import com.FitMeet.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/sendMessage") // Nhận tin nhắn từ client tại "/app/sendMessage"
    @SendTo("/topic/messages") // Gửi tin nhắn tới tất cả subscriber tại "/topic/messages"
    public ChatMessage sendMessage(ChatMessage message) {
        System.out.println("Received message: " + message);
        return message; // Gửi lại tin nhắn nhận được
    }
}