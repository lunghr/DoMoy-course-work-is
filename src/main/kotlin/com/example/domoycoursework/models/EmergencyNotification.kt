package com.example.domoycoursework.models

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter



class EmergencyNotification(
    val id: Int,
    var title: String,
    var description: String,
    var date: String,
    var houseId: Int,
    var adminId: Int
)