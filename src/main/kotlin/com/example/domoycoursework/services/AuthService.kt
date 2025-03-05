package com.example.domoycoursework.services

import com.example.domoycoursework.models.SecretKey
import com.example.domoycoursework.models.Admin
import com.example.domoycoursework.dto.AdminRegisterRequest
import com.example.domoycoursework.dto.AuthRequest
import com.example.domoycoursework.dto.AuthResponse
import com.example.domoycoursework.models.User
import com.example.domoycoursework.dto.UserRegisterRequest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private var userService: UserService,
    private var adminService: AdminService,
    private var authenticationManager: AuthenticationManager,
    private var passwordEncoder: PasswordEncoder,
    private var jwtService: JwtService
) {
    fun registerUser(request: UserRegisterRequest): AuthResponse {
        val user = User(
            id = 0,
            email = request.email,
            phoneNumber = request.phoneNumber,
            password = passwordEncoder.encode(request.password)
        )

        println("${user.email}, ${user.phoneNumber}, ${user.password}, ${user.role}")

        userService.createUser(user)
        println("User created")
        return AuthResponse(jwtService.generateToken(user))
    }

    fun registerAdmin(request: AdminRegisterRequest): AuthResponse {
        val admin = Admin(
            id = 0,
            email = request.email,
            phoneNumber = request.phoneNumber,
            password = passwordEncoder.encode(request.password)
        )
        val key = SecretKey(
            id = 0L,
            key = request.secretKey
        )

        adminService.createAdmin(admin, key)
        println("Admin created")
        return AuthResponse(jwtService.generateToken(admin))
    }

    fun login(request: AuthRequest): AuthResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.username, request.password)
        )
        val authenticatedUser = userService.loadUserByPhoneNumber(request.username)
            ?: userService.loadUserByEmail(request.username)
            ?: adminService.loadAdminByPhoneNumber(request.username)
            ?: adminService.loadAdminByEmail(request.username)
            ?: throw Exception("User with username $request.username not found")

        return AuthResponse(jwtService.generateToken(authenticatedUser))
    }

    fun getUsernameFromToken(token: String): String {
        return jwtService.getUsername(jwtService.extractToken(token))
    }


    fun getRoleFromToken(token: String): String {
        return jwtService.getRole(jwtService.extractToken(token))
    }

    fun refreshToken(token: String): String {
        val username = jwtService.getUsername(jwtService.extractToken(token))
        val userDetails = userService.loadUserByEmail(username)
            ?: adminService.loadAdminByEmail(username)
        return jwtService.generateToken(userDetails ?: throw UsernameNotFoundException("User or Admin not found"))

    }
}
