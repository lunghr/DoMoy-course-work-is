package com.example.domoycoursework.repos

import com.example.domoycoursework.models.SecretKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface SecretKeyRepository: JpaRepository<SecretKey, Long> {
    fun findSecretKeyById(id: Long): SecretKey?
    fun findSecretKeyByKey(key: String): SecretKey?
    fun findSecretKeyByAdminId(id: Long): SecretKey?
}