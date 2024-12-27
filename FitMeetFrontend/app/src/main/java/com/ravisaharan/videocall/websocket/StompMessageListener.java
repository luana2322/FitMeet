package com.ravisaharan.videocall.websocket;

public interface StompMessageListener {

    void onMessage(StompMessage message);

}