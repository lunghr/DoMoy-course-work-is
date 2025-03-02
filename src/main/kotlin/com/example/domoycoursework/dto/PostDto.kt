package com.example.domoycoursework.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Post DTO")
class PostDto (
    @Schema(description = "Post title", example = "Title")
    var title: String,

    @Schema(description = "Post content", example = "Content")
    var content: String,
)