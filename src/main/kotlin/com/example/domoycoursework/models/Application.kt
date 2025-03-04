package com.example.domoycoursework.models

import com.example.domoycoursework.enums.ApplicationStatus
import com.example.domoycoursework.enums.ApplicationTheme
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
@Table(name = "applications")
class Application(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    var status: ApplicationStatus = ApplicationStatus.NEW,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @ElementCollection
    @Column(name = "filenames", nullable = true)
    var filenames: List<String> = mutableListOf(),

    @Column(name = "theme", nullable = false)
    @Enumerated(EnumType.STRING)
    var theme: ApplicationTheme,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "description", nullable = false)
    var description: String,

    @Column(name = "date", nullable = false)
    @JsonFormat(pattern = "HH:mm dd.MM.yy")
    var createdAt: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yy")),

    @OneToMany(mappedBy = "application", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonManagedReference
    var responses: List<ApplicationResponse> = mutableListOf()
)