package com.example.domoycoursework.services

import com.example.domoycoursework.repos.SecretKeyRepository
import com.example.domoycoursework.exceptions.InvalidSecretKeyException
import com.example.domoycoursework.models.SecretKey
import com.example.domoycoursework.models.User
import org.springframework.stereotype.Service
import java.util.UUID


@Service
class SecretKeyService(
    private val secretKeyRepository: SecretKeyRepository
) {

    fun createSecretKey(): String {
        val key = UUID.randomUUID().toString().substring(0, 32)
        secretKeyRepository.findByKey(key)?.let { createSecretKey() }
        val secretKey = SecretKey(
            key = key,
            used = false,
        )
        secretKeyRepository.save(secretKey)
        return key
    }

    fun useSecretKey(key: String, user: User) {
        secretKeyRepository.findByKey(key)?.let {
            if (it.used) {
                throw InvalidSecretKeyException("Invalid secret key")
            }
            it.markAsUsed(user)
        } ?: throw InvalidSecretKeyException("Invalid secret key")
    }

    fun getAllAvailableKeys(): List<SecretKey> {
        return secretKeyRepository.findAllByUsedIsFalse()
    }
}