package com.example.domoycoursework.dto

import jakarta.validation.constraints.NotBlank

class HouseDto(
    @field:NotBlank(message = "City is required")
    var city: String,

    @field:NotBlank(message = "Street is required")
    var street: String,

    @field:NotBlank(message = "House number is required")
    var houseNumber: String,

    var zipCode: String? = null,

    var totalFloors: Int
)