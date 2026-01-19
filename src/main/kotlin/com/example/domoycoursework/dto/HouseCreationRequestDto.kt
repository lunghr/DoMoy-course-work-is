package com.example.domoycoursework.dto

import jakarta.validation.constraints.NotBlank

class HouseCreationRequestDto(
    @field:NotBlank(message = "Address is required")
    var address: String
)