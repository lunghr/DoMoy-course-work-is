package com.example.domoycoursework.controllers

import com.example.domoycoursework.dto.AuthRequest
import com.example.domoycoursework.dto.AuthResponse
import com.example.domoycoursework.dto.UserRegisterRequest
import com.example.domoycoursework.services.AuthService
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
class AuthController(
    private var authService: AuthService
) {
    @PostMapping("/login")
    fun login(@RequestBody @Valid authRequest: AuthRequest): AuthResponse {
        return authService.login(authRequest)
    }

    @PostMapping("/register-user")
    fun register(@RequestBody @Valid registerRequest: UserRegisterRequest): AuthResponse {
        return authService.registerUser(registerRequest)
    }

    @GetMapping("/username")
    fun getUsername(@RequestHeader("Authorization") token: String): String {
        return authService.getUsernameFromToken(token)
    }

    @GetMapping("/role")
    fun getRole(@RequestHeader("Authorization") token: String): String {
        return authService.getRoleFromToken(token)
    }
}
