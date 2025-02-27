package com.example.domoycoursework.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Suppress("DEPRECATION")
@Schema(description = "Sign in request DTO")
class AuthRequest(

    @Schema(description = "Email or Phone number", example = "oleg@gmail.ru or +780123456789")
    @NotBlank(message = "Email or Phone number is required")
    var username: String,

    @Schema(description = "Password", example = "123456")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @NotBlank(message = "Password is required")
    var password: String
)


