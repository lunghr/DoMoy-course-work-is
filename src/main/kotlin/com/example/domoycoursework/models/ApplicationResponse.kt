package com.example.domoycoursework.models

import com.example.domoycoursework.enums.ApplicationStatus
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ApplicationResponse(
    var id: Int,
    var applicationId: Int,
    var adminId: Int,
    var response: String,
    var status: ApplicationStatus,
    var date: String
)