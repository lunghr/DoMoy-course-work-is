package com.example.domoycoursework.models

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
@Table(name = "chats")
class Chat (
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "name", nullable = false)
    var name: String,

    @OneToMany(mappedBy = "chat")
    @JsonManagedReference
    var messages: List<Message> = mutableListOf()
)