package com.example.domoycoursework.models

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Entity
@Table(name = "emergency_notifications")
class EmergencyNotification(
    @Id
    @Column(name = "notification_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "description", nullable = false)
    var description: String,

    @Column(name = "date", nullable = false)
    @JsonFormat(pattern = "HH:mm dd.MM.yy")
    var date: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yy")),

    var houseId: Int,

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    var admin: Admin
)