package com.example.domoycoursework.users.admin.secretKeys.services

import com.example.domoycoursework.users.admin.secretKeys.models.SecretKey
import com.example.domoycoursework.users.admin.secretKeys.repos.SecretKeyRepository
import com.example.domoycoursework.users.admin.models.Admin
import com.example.domoycoursework.exceptions.models.InvalidSecretKey
import org.springframework.stereotype.Service
import java.util.UUID


@Service
class SecretKeyService(
    private val secretKeyRepository: SecretKeyRepository
) {

    fun createSecretKey(): String {
        val key = UUID.randomUUID().toString().substring(0, 12)
        secretKeyRepository.findSecretKeyByKey(key)?.let {
            createSecretKey()
        } ?: secretKeyRepository.save(SecretKey(id = 0L, key = key))

        return key
    }

    fun useSecretKey(key: String, admin: Admin) {
        secretKeyRepository.findSecretKeyByKey(key)?.let {
            if (it.isUsed == true) throw InvalidSecretKey("Secret key already used") else it.isUsed = true
            it.admin = admin
            secretKeyRepository.save(it)
        }
            ?: throw InvalidSecretKey("Secret key not found")
    }
}