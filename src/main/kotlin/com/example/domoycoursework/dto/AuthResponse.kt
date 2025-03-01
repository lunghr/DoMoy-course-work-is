package com.example.domoycoursework.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Token response DTO")
class AuthResponse(
    @Schema(
        description = "Access token",
        example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvYmxlZyIsImV4cCI6MTYzNzQwNjQwMH0.7"
    )
    var accessToken: String,
)
