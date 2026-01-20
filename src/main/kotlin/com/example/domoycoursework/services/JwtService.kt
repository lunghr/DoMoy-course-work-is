package com.example.domoycoursework.services

import com.example.domoycoursework.models.User
import io.github.cdimascio.dotenv.Dotenv
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Date
import javax.crypto.spec.SecretKeySpec
import kotlin.collections.HashMap

@Service
class JwtService {
    private var dotenv = Dotenv.load()
    private var secret = dotenv["SECRET"]

    fun generateToken(userDetails: UserDetails): String {
        val claims = HashMap<String, Any?>()
        if (userDetails is User) {
            claims["id"] = userDetails.id
            claims["email"] = userDetails.email
            claims["role"] = userDetails.role
        }
        return Jwts.builder()
            .subject(userDetails.username)
            .claims(claims)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .signWith(signingKey)
            .compact()
    }

    private val signingKey: SecretKeySpec
        get() {
            val keyBytes = Decoders.BASE64.decode(secret)
            return SecretKeySpec(keyBytes, 0, keyBytes.size, "HmacSHA256")
        }

    fun <T> getClaim(token: String, resolver: (Claims) -> T): T =
        Jwts.parser()
            .setSigningKey(signingKey)
            .build()
            .parseClaimsJws(token)
            .body
            .let(resolver)

    fun getUsername(token: String): String = getClaim(token) { it.subject }

    fun getUserId(token: String): Long = getClaim(token) { (it["id"] as Long) }

    fun getRole(token: String): String = getClaim(token) { it["role"] as String }

    fun extractToken(token: String): String = token.removePrefix("Bearer ")

    fun getTokenFromHeader(request: HttpServletRequest): String? =
        request.getHeader("Authorization")?.takeIf { it.startsWith("Bearer ") }?.let { extractToken(it) }

    fun getExpiration(token: String): Date = getClaim(token) { it.expiration }

    fun isTokenExpired(token: String): Boolean = getExpiration(token) < Date()

    fun validateToken(token: String, userDetails: UserDetails): Boolean =
        getUsername(token) == userDetails.username && !isTokenExpired(token)
}
