package com.example.domoycoursework.config

import com.example.domoycoursework.exceptions.NotFoundException
import com.example.domoycoursework.services.AdminService
import com.example.domoycoursework.services.JwtService
import com.example.domoycoursework.services.UserService
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Service
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.HandshakeInterceptor

@Service
//@Component
class WebSocketAuthInterceptor(
    private val jwtService: JwtService,
    private val userService: UserService,
    private val adminService: AdminService
):HandshakeInterceptor {
    override fun beforeHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String, Any>
    ): Boolean {
        println(request.headers.getFirst("Authorization"))
        val jwt = request.headers.getFirst("Authorization")?.removePrefix("Bearer ")
        val username = jwt?.let { jwtService.getUsername(it) }
        println("Received username: $username")
        println("Auth: ${SecurityContextHolder.getContext().authentication}")

        return username != null && SecurityContextHolder.getContext().authentication != null
    }

    override fun afterHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        exception: Exception?
    ) {
    }
}