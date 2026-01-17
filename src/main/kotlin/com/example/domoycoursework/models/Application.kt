package com.example.domoycoursework.models

import com.example.domoycoursework.models.enums.ApplicationStatus
import com.example.domoycoursework.models.enums.ApplicationTheme


class Application(
    var id: Int,
    var status: ApplicationStatus = ApplicationStatus.NEW,
    var userId: Int,
    var theme: ApplicationTheme,
    var title: String,
    var description: String,
    var createdAt: String,
)