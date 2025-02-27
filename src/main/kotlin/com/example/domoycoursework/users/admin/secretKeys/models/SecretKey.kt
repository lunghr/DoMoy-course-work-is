package com.example.domoycoursework.users.admin.secretKeys.models

import com.example.domoycoursework.users.admin.models.Admin
import jakarta.persistence.*
import jakarta.validation.constraints.Size


@Entity
@Table(name = "secret_keys")
data class SecretKey(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long,

    @Column(name = "key", nullable = false)
    @Size(min=12, max = 12, message = "Secret key must be 12 characters long")
    var key: String,

    @Column(name = "is_used", nullable = false) var isUsed: Boolean ?= false,

    @OneToOne
    @JoinColumn(name = "admin_id", nullable = true) var admin: Admin? = null
)