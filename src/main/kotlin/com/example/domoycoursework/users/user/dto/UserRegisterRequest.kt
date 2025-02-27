package com.example.domoycoursework.users.user.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.UniqueElements

@Schema(description = "Sign up request DTO")
class UserRegisterRequest(
    @Schema(description = "Phone number", example = "+780123456789")
    var phoneNumber: String,

    @Schema(description = "Email", example = "lalal@gmail.com")
    @UniqueElements(message = "Email already in use")
    @NotBlank(message = "Email is required")
    var email: String,

    @Schema(description = "Password", example = "123456")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @NotBlank(message = "Password is required")
    var password: String
)
