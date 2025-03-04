package com.example.domoycoursework.config

import com.example.domoycoursework.models.Chat
import com.example.domoycoursework.repos.ChatRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class ChatInitializer(
    private val chatRepository: ChatRepository,
    private val webSocketAuthInterceptor: WebSocketAuthInterceptor
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        if (chatRepository.count() == 0L) { // Проверяем, есть ли уже чаты
            val chats = listOf(
                Chat(id = 0, name = "General Discussion"),
                Chat(id = 0, name = "Resale"),
                Chat(id = 0, name = "Hobbies")
            )
            chatRepository.saveAll(chats)
            println("Chats are successfully created")
        } else {
            println("Chats are already exist")
        }
    }


}
