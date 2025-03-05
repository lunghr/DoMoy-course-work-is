//package com.example.domoycoursework.services
//
//import com.example.domoycoursework.dto.MessageDto
//import com.example.domoycoursework.exceptions.NotFoundException
//import com.example.domoycoursework.exceptions.UserNotFoundException
//import com.example.domoycoursework.models.Admin
//import com.example.domoycoursework.models.Message
//import com.example.domoycoursework.models.User
//import com.example.domoycoursework.repos.ChatRepository
//import com.example.domoycoursework.repos.MessageRepository
//import org.springframework.stereotype.Service
//
//@Service
//class ChatService(
//    private val chatRepository: ChatRepository,
//    private val messageRepository: MessageRepository,
//    private val userService: UserService,
//    private val jwtService: JwtService,
//    private val adminService: AdminService
//) {
//
//    fun saveMessage(messageDto: MessageDto): Message {
//        return chatRepository.findChatById(messageDto.chatId)?.let { chat ->
//            val author = userService.loadUserByEmail(jwtService.getUsername(jwtService.extractToken(messageDto.token)))
//                ?: adminService.loadAdminByEmail(jwtService.getUsername(jwtService.extractToken(messageDto.token)))
//            val message = Message(
//                id = 0L,
//                chat = chat,
//                user = author.takeIf { it is User } as? User,
//                admin = author.takeIf { it is Admin } as? Admin,
//                text = messageDto.text
//            )
//            return messageRepository.save(message)
//        } ?: throw NotFoundException("Chat with id ${messageDto.chatId} not found")
//    }
//
//    fun getMessages(chatId: Long): List<Message> {
//        return chatRepository.findChatById(chatId)?.messages ?: throw NotFoundException("Chat with id $chatId not found")
//    }
//}