package com.FitMeet.config;



import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Cấu hình broker cho client nhận message
        config.enableSimpleBroker("/topic"); // Broker sẽ xử lý các topic
        config.setApplicationDestinationPrefixes("/app"); // Prefix cho các endpoint gửi từ client
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Đăng ký endpoint WebSocket
        registry.addEndpoint("/my-ws").setAllowedOrigins("*").withSockJS(); // Kích hoạt fallback SockJS
    }
}
