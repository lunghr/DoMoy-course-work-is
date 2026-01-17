package com.example.domoycoursework.models

import com.example.domoycoursework.models.enums.RequestStatus


class TSJRequest (
    val id: Int,
    var userId: Int,
    var status: RequestStatus
)