package com.example.domoycoursework.dto

import com.example.domoycoursework.models.enums.RequestStatus
import io.swagger.v3.oas.annotations.media.Schema


@Schema(description = "TSJ response DTO")
class TSJResponseDto (
    @Schema(description = "TSJ Request Id", example = "1")
    val id: Int,

    @Schema(description = "User Id", example = "1")
    val userId: Int,

    @Schema(description = "Status", example = "ACCEPTED")
    val status: RequestStatus
)