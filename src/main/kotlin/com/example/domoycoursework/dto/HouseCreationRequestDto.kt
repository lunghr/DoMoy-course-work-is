package com.example.domoycoursework.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank


@Schema(description = "House creation request DTO")
class HouseCreationRequestDto(
    @Schema(description = "House address", example = "Moscow, Red Square")
    @NotBlank(message = "Address is required")
    var address: String
)