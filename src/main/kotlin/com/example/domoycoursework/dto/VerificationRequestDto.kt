package com.example.domoycoursework.dto

import io.swagger.v3.oas.annotations.media.Schema


@Schema(description = "Additional user data DTO")
class VerificationRequestDto (
    @Schema(description = "First name", example = "Oleg")
    val firstName: String,
    @Schema(description = "Last name", example = "Olegov")
    val lastName: String,
    @Schema(description = "Cadastral Number", example = "123456789")
    val cadastralNumber: Long,
    @Schema(description = "Address", example = "Moscow, Red Square")
    val address: String,
    @Schema(description = "Flat number", example = "123")
    val flatNumber: Int,
)