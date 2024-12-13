package com.FitMeet.service;

import com.FitMeet.model.Chat;

import java.util.List;

public interface ChatService {
    List<Chat> findAll();

    Chat findById(Long id);

    void deteleById(Long id);

    Chat save(Chat attachment);

    Chat update(Chat attachment);
}
