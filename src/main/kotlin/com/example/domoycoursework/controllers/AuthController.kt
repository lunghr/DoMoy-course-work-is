package com.example.domoycoursework.controllers

import com.example.domoycoursework.dto.AdminRegisterRequest
import com.example.domoycoursework.dto.AuthRequest
import com.example.domoycoursework.dto.AuthResponse
import com.example.domoycoursework.services.AuthService
import com.example.domoycoursework.dto.UserRegisterRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = ["*"])
@Tag(name = "Authentication")
class AuthController(
    private var authService: AuthService
) {

    @Operation(summary = "Sign in")
    @PostMapping("/login")
    fun login(@RequestBody @Valid authRequest: AuthRequest): AuthResponse {
        return authService.login(authRequest)
    }

    @Operation(summary = "Sign up for users")
    @PostMapping("/register-user")
    fun register(@RequestBody @Valid registerRequest: UserRegisterRequest): AuthResponse {
        return authService.registerUser(registerRequest)
    }

    @Operation(summary = "Sign up for admins")
    @PostMapping("/register-admin")
    fun registerAdmin(@RequestBody @Valid registerRequest: AdminRegisterRequest): AuthResponse {
        return authService.registerAdmin(registerRequest)
    }

    @Operation(summary = "Get username from token")
    @GetMapping("/username")
    fun getUsername(@RequestHeader("Authorization") token: String): String {
        return authService.getUsernameFromToken(token)
    }

    @Operation(summary = "Get user role")
    @GetMapping("/role")
    fun getRole(@RequestHeader("Authorization") token: String): String {
        return authService.getRoleFromToken(token)
    }

    @Operation(summary = "Refresh token")
    @GetMapping("/refresh")
    fun refreshToken(@RequestHeader("Authorization") token: String): String {
        return authService.refreshToken(token)
    }
}
