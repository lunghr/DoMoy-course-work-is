package com.example.domoycoursework.config

import com.example.domoycoursework.services.JwtService
import com.example.domoycoursework.services.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val jwtService: JwtService,
    private val userService: UserService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if ("OPTIONS" == request.method) {
            response.status = HttpServletResponse.SC_OK
            response.setHeader("Access-Control-Allow-Origin", "*")
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
            response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, X-Requested-With")
            return
        }

        val jwt = jwtService.getTokenFromHeader(request)
        val username = jwt?.let { jwtService.getUsername(it) }
        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val authenticatedUser = userService.findUser(username)
            println(authenticatedUser.role)
            if (jwtService.validateToken(jwt, authenticatedUser)) {
                val authToken =
                    UsernamePasswordAuthenticationToken(authenticatedUser, null, authenticatedUser.authorities).apply {
                        details = WebAuthenticationDetailsSource().buildDetails(request)
                    }
                SecurityContextHolder.getContext().authentication = authToken
            }
        }
        filterChain.doFilter(request, response)
    }
}
