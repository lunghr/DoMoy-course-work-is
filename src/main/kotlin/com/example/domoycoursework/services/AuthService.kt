package com.example.domoycoursework.services

import com.example.domoycoursework.dto.AdminRegisterRequest
import com.example.domoycoursework.dto.AuthRequest
import com.example.domoycoursework.dto.AuthResponse
import com.example.domoycoursework.models.User
import com.example.domoycoursework.dto.UserRegisterRequest
import com.example.domoycoursework.models.SecretKey
import com.example.domoycoursework.models.enums.Role
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private var userService: UserService,
    private var authenticationManager: AuthenticationManager,
    private var passwordEncoder: PasswordEncoder,
    private var jwtService: JwtService
) {
    fun registerUser(request: UserRegisterRequest): AuthResponse {
        val user = User(
            email = request.email,
            phoneNumber = request.phoneNumber,
            password = passwordEncoder.encode(request.password)
        )
        userService.createUser(user)
        println("User created")
        return AuthResponse(jwtService.generateToken(user))
    }

    fun registerAdmin(request: AdminRegisterRequest): AuthResponse {
        println("hi")
        val admin = User(
            email = request.email,
            phoneNumber = request.phoneNumber,
            password = passwordEncoder.encode(request.password),
            role = Role.ADMIN
        )
        val key = SecretKey(
            id = 0,
            key = request.secretKey ?: "",
        )

        userService.createAdmin(admin, key)
        return AuthResponse(jwtService.generateToken(admin))
    }

    fun login(request: AuthRequest): AuthResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.username, request.password)
        )
        val authenticatedUser = userService.findUser(request.username)
        return AuthResponse(jwtService.generateToken(authenticatedUser))
    }

    fun getUsernameFromToken(token: String): String {
        return jwtService.getUsername(jwtService.extractToken(token))
    }


    fun getRoleFromToken(token: String): String {
        return jwtService.getRole(jwtService.extractToken(token))
    }
}
