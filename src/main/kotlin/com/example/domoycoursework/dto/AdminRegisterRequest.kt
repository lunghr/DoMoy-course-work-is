package com.example.domoycoursework.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class AdminRegisterRequest(
    var phoneNumber: String,

    @field:NotBlank(message = "Email is required")
    var email: String,

    var secretKey: String? = null,

    @field:Size(min = 6, message = "Password must be at least 6 characters long")
    @field:NotBlank(message = "Password is required")
    var password: String
)