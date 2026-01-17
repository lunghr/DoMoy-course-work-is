package com.example.domoycoursework.models

import com.example.domoycoursework.models.enums.ApplicationStatus

class ApplicationResponse(
    var id: Int,
    var applicationId: Int,
    var adminId: Int,
    var response: String,
    var status: ApplicationStatus,
    var date: String
)