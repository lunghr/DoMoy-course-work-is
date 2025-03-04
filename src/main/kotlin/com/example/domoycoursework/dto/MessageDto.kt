package com.example.domoycoursework.dto


data class MessageDto(
    val text: String,
    val chatId: Long,
    val token: String // Токен пользователя

)