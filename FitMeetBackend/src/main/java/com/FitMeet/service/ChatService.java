package com.FitMeet.service;

import com.FitMeet.model.Chat;

import java.util.List;

public interface ChatService {
    List<Chat> findAll();

    List<Chat> findBySenderOrReceiver(int senderID, int receiverID);

    List<Chat> findByReceiver(int receiverID);

    void save(Chat theChat);
}
