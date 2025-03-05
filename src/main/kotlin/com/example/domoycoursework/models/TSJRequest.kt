package com.example.domoycoursework.models

import com.example.domoycoursework.enums.RequestStatus
import jakarta.persistence.*


class TSJRequest (
    val id: Int,
    var userId: Int,
    var status: RequestStatus
)