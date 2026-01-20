package com.example.domoycoursework.repos

import com.example.domoycoursework.models.SecretKey
import org.springframework.data.jpa.repository.JpaRepository

interface SecretKeyRepository : JpaRepository<SecretKey, Long> {
    fun findByKey(key: String): SecretKey?
    fun findAllByUsedIsFalse(): List<SecretKey>
}