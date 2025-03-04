package com.example.domoycoursework.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "ApplicationRequestDto", description = "Data Transfer Object for Application Request")
class ApplicationRequestDto(
    @Schema(description = "Theme of the request", example = "Cleaning")
    var theme: String,

    @Schema(description = "Title of the request", example = "Bad cleaning")
    var title: String,

    @Schema(description = "Description of the request", example = "The cleaning was not done properly")
    var description: String
)