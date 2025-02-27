package com.example.domoycoursework.websocket

import org.springframework.stereotype.Service
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.CopyOnWriteArraySet

@Service
class NotificationHandler : TextWebSocketHandler() {
    private val sessions = CopyOnWriteArraySet<WebSocketSession>()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessions.add(session)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessions.remove(session)
    }

    @Throws(Exception::class)
    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val payload = message.payload
        if ("ping".equals(payload, ignoreCase = true)) {
            session.sendMessage(TextMessage("pong"))
        }
    }

    fun broadcast(message: String?) {
        val textMessage = TextMessage(message!!)
        for (session in sessions) {
            if (session.isOpen) {
                try {
                    session.sendMessage(textMessage)
                } catch (ignore: Exception) {}
            }
        }
    }
}
