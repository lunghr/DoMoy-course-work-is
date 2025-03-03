package com.example.domoycoursework.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "EmergencyNotificationDto", description = "Data Transfer Object for Emergency Notification")
class EmergencyNotificationDto (
    @Schema(description = "Title of the notification", example = "Fire in the house")
    var title: String,

    @Schema(description = "Description of the notification", example = "Fire in the house, please evacuate")
    var description: String,

    @Schema(description = "House ID", example = "1")
    var houseId: Long
)