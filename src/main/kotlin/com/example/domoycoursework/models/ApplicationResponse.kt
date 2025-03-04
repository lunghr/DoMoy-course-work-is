package com.example.domoycoursework.models

import com.example.domoycoursework.enums.ApplicationStatus
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
@Table(name = "applications_responses")
class ApplicationResponse(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    @JsonBackReference
    var application: Application,

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    var admin: Admin,

    @Column(name = "response", nullable = false)
    var response: String,

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    var status: ApplicationStatus,

    @Column(name = "date", nullable = false)
    @JsonFormat(pattern = "HH:mm dd.MM.yy")
    var date: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yy")),
)