package com.example.domoycoursework.users.admin.secretKeys.repos

import com.example.domoycoursework.users.admin.secretKeys.models.SecretKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface SecretKeyRepository: JpaRepository<SecretKey, Long> {
    fun findSecretKeyById(id: Long): SecretKey?
    fun findSecretKeyByKey(key: String): SecretKey?
    fun findSecretKeyByAdminId(id: Long): SecretKey?
}