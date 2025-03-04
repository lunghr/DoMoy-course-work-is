package com.example.domoycoursework.repos

import com.example.domoycoursework.models.Chat
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRepository: JpaRepository<Chat, Long> {
    fun findChatById(id: Long): Chat?
}