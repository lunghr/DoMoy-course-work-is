package com.example.domoycoursework.models

import com.example.domoycoursework.models.Admin
import jakarta.persistence.*
import jakarta.validation.constraints.Size


data class SecretKey(
    private val id: Int,
    var key: String,
    var isUsed: Boolean ?= false,
    var adminId: Int? = null
)