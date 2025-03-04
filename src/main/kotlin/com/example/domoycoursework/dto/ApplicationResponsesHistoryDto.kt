package com.example.domoycoursework.dto

import com.example.domoycoursework.enums.ApplicationStatus
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "ApplicationResponsesHistoryDto", description = "DTO for ApplicationResponsesHistory")
class ApplicationResponsesHistoryDto (
    @Schema(description = "Responses_id")
    var responses: Map<Long, ApplicationStatus>
)