package com.example.domoycoursework.models

import com.example.domoycoursework.enums.RequestStatus
import jakarta.persistence.*


@Entity
@Table(name = "tsj_requests")
class TSJRequest (
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long,

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    var status: RequestStatus
)