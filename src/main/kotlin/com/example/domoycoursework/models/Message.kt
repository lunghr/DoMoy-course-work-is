package com.example.domoycoursework.models

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
@Table(name = "messages")
class Message(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    @JsonBackReference
    var chat: Chat,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    var user: User? = null,

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = true)
    var admin: Admin? = null,

    @Column(name = "text", nullable = false)
    var text: String,

    @Column(name = "sent_data", nullable = false)
    @JsonFormat(pattern = "HH:mm dd.MM.yy")
    var sentAt: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yy"))

)