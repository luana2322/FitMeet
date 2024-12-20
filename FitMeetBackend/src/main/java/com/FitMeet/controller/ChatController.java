package com.FitMeet.controller;

import com.FitMeet.model.ChatMessage;
import com.FitMeet.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ChatController {


    @MessageMapping("/sendMessage") // Nhận tin nhắn từ client tại /app/sendMessage
    @SendTo("/topic/messages") // Gửi tới tất cả subscribers tại /topic/messages
    public Message sendMessage(Message message) {
        message.setTimestamp(System.currentTimeMillis() + "");
        return message; // Tin nhắn được gửi đến tất cả người nghe
    }
}