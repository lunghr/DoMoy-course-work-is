package com.example.domoycoursework.models

import com.example.domoycoursework.exceptions.InvalidSecretKeyException
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDateTime


@Entity
@Table(name = "secret_keys")
data class SecretKey(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true, nullable = false, length = 32)
    val key: String,

    @Column(nullable = false)
    var used: Boolean = false,

    @Column(nullable = true)
    var usedAt: LocalDateTime? = null,

    @Column(nullable = true)
    var usedByEmail: String? = null
) {
    fun markAsUsed(byUser: User) {
        if (used) throw InvalidSecretKeyException("Invalid secret key")
        this.used = true
        this.usedAt = LocalDateTime.now()
        this.usedByEmail = byUser.email
    }
}