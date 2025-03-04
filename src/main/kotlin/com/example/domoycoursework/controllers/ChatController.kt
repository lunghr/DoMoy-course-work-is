package com.example.domoycoursework.controllers

import com.example.domoycoursework.dto.MessageDto
import com.example.domoycoursework.models.Message
import com.example.domoycoursework.services.ChatService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.messaging.handler.annotation.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = ["*"])
@Tag(name = "Chat")
class ChatController(
    private val chatService: ChatService
) {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/chat/{chatId}")
    fun sendMessage(
        @Payload messageDto: MessageDto,
    ): Map<String, String> {
        val message = chatService.saveMessage(messageDto)
        val chatName = message.user?.chatName ?: message.admin?.chatName ?: throw IllegalStateException("Chat name is missing")
        return mapOf("chatName" to chatName, "text" to message.text)
    }

    @GetMapping("/{chatId}/messages")
    fun getChatMessages(@PathVariable chatId: Long): List<Message> {
        return chatService.getMessages(chatId)
    }
}
