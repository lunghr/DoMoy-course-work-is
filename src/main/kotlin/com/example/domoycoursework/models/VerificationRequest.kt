package com.example.domoycoursework.models

import com.example.domoycoursework.enums.RequestStatus
import jakarta.persistence.*

@Entity
@Table(name = "verification_requests")
class VerificationRequest(

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "first_name", nullable = false)
    var firstName: String,

    @Column(name = "last_name", nullable = false)
    var lastName: String,

    @Column(name = "cadastral_number", nullable = false)
    var cadastralNumber: String,

    @Column(name = "address", nullable = false)
    var address: String,

    @Column(name = "flat_number", nullable = false)
    var flatNumber: Int,

    @OneToOne
    @JoinColumn(name = "user_id")
    var user: User,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: RequestStatus = RequestStatus.PENDING
)