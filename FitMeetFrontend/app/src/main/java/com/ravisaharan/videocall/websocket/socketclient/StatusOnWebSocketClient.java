package com.ravisaharan.videocall.websocket.socketclient;



import com.ravisaharan.videocall.websocket.CloseHandler;

import okhttp3.Response;
import okhttp3.WebSocket;

public class StatusOnWebSocketClient extends SpringBootWebSocketClient {

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        sendConnectMessage(webSocket);
        for (String topic : topics.keySet()) {
            sendSubscribeMessage(webSocket, topic);
        }
        //turn the User with givenID's status On
        System.out.println("Called on Open");
        sendMessage(webSocket, "/app/userList/status/on", "1");

        closeHandler = new CloseHandler(webSocket);
    }
}