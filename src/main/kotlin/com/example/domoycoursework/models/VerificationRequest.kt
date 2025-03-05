package com.example.domoycoursework.models

import com.example.domoycoursework.enums.RequestStatus
import jakarta.persistence.*

class VerificationRequest(
    val id: Int,
    var firstName: String,
    var lastName: String,
    var cadastralNumber: String,
    var address: String,
    var flatNumber: Int,
    var userId: Int,
    var status: RequestStatus = RequestStatus.PENDING
)