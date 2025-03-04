package com.example.domoycoursework.config

import com.example.domoycoursework.websocket.NotificationHandler
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.*

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig(private val webSocketAuthInterceptor: WebSocketAuthInterceptor) :
    WebSocketMessageBrokerConfigurer {
    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws/chat", "/ws/notifications", "/ws/other")  // Перечисляем несколько маршрутов
            .setAllowedOrigins("*")
//            .addInterceptors(webSocketAuthInterceptor)
    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.enableSimpleBroker("/topic")
        registry.setApplicationDestinationPrefixes("/app")
    }
}
