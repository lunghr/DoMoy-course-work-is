package com.example.domoycoursework.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Additional data for admin")
class AdminAdditionalDataDto (
    @Schema(description = "First name")
    var firstName: String,

    @Schema(description = "Last name")
    var lastName: String
)