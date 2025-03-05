package com.example.domoycoursework.models

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date


class Post (
    val id: Int,
    var title: String,
    var content: String,
    var createdAt: String,
    var fileName: String? = null,
    var author: String
)