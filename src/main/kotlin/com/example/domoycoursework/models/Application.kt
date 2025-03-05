package com.example.domoycoursework.models

import com.example.domoycoursework.enums.ApplicationStatus
import com.example.domoycoursework.enums.ApplicationTheme
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class Application(
    var id: Int,
    var status: ApplicationStatus = ApplicationStatus.NEW,
    var userId: Int,
    var theme: ApplicationTheme,
    var title: String,
    var description: String,
    var createdAt: String,
)