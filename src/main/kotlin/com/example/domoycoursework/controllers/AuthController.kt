package com.example.domoycoursework.controllers

import com.example.domoycoursework.dto.AdminRegisterRequest
import com.example.domoycoursework.dto.AuthRequest
import com.example.domoycoursework.dto.AuthResponse
import com.example.domoycoursework.dto.UserRegisterRequest
import com.example.domoycoursework.services.AuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = ["*"])
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(@RequestBody @Valid authRequest: AuthRequest): ResponseEntity<AuthResponse> {
        return ResponseEntity.ok(authService.login(authRequest))
    }

    @PostMapping("/register-user")
    fun registerUser(@RequestBody @Valid registerRequest: UserRegisterRequest): ResponseEntity<AuthResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerUser(registerRequest))
    }

    @PostMapping("/register-admin")
    fun registerAdmin(@RequestBody @Valid registerRequest: AdminRegisterRequest): ResponseEntity<AuthResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerAdmin(registerRequest))
    }

    @GetMapping("/username")
    fun getUsername(@RequestHeader("Authorization") token: String): ResponseEntity<String> {
        return ResponseEntity.ok(authService.getUsernameFromToken(token))
    }

    @GetMapping("/role")
    fun getRole(@RequestHeader("Authorization") token: String): ResponseEntity<String> {
        return ResponseEntity.ok(authService.getRoleFromToken(token))
    }
}