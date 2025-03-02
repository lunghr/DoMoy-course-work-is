package com.example.domoycoursework.models

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date


@Entity
@Table(name = "posts")
class Post (
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "content", nullable = false)
    var content: String,

    @Column(name = "created_at", nullable = false)
    @JsonFormat(pattern = "HH:mm dd.MM.yy")
    var createdAt: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yy")),

    @Column(name = "file_name", nullable = true)
    var fileName: String? = null,

    @Column(name = "author", nullable = false)
    var author: String
)