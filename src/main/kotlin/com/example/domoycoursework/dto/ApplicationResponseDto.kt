package com.example.domoycoursework.dto

import io.swagger.v3.oas.annotations.media.Schema


@Schema(name = "ApplicationResponseDto", description = "DTO for ApplicationResponse")
class ApplicationResponseDto(
    @Schema(description = "Text response", example = "Your application has been accepted")
    var response: String,

    @Schema(description = "New application status", example = "COMPLETED")
    var status: String
)