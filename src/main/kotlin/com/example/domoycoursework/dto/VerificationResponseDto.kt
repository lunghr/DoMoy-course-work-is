package com.example.domoycoursework.dto

import com.example.domoycoursework.enums.RequestStatus
import io.swagger.v3.oas.annotations.media.Schema


@Schema(description = "Verification response DTO")
class VerificationResponseDto(
    @Schema(description = "Verification Request Id", example = "1")
    var id: Int,

    @Schema(description = "User Id", example = "1")
    var userId: Int,

    @Schema(description = "First name", example = "Oleg")
    var firstName: String,

    @Schema(description = "Last name", example = "Olegov")
    var lastName: String,

    @Schema(description = "Cadastral Number", example = "381981319")
    var cadastralNumber: String,

    @Schema(description = "Flat Number", example = "1")
    var flatNumber: Int,

    @Schema(description = "address", example = "Moscow, Red Square, 1")
    var address: String,

    @Schema(description = "Verification status", example = "true")
    var status: RequestStatus
)