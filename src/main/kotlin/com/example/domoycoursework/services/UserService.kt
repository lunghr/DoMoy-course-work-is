package com.example.domoycoursework.services

import com.example.domoycoursework.exceptions.EmailAlreadyInUseException
import com.example.domoycoursework.exceptions.PhoneNumberAlreadyInUseException
import com.example.domoycoursework.exceptions.UserNotFoundException
import com.example.domoycoursework.models.SecretKey
import com.example.domoycoursework.models.User
import com.example.domoycoursework.models.enums.Role
import com.example.domoycoursework.repos.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserService(
    private var userRepository: UserRepository,
    private var secretKeyService: SecretKeyService
) {
    @Transactional
    fun createUser(user: User): User =
        userRepository.findByEmail(user.email)?.let { throw Exception("User already exists") }
            ?: userRepository.save(user)

    @Transactional
    fun createAdmin(user: User, secretKey: SecretKey): User {
        getAdmins().takeIf { it.isEmpty() }?.let { return userRepository.save(user) }

        userRepository.findByEmail(user.email)
            ?.let { throw EmailAlreadyInUseException("This email address already in use") }
        userRepository.findByPhoneNumber(user.phoneNumber)
            ?.let { throw PhoneNumberAlreadyInUseException("This phone number already in use") }
        val admin = userRepository.save(user)
        try {
            secretKeyService.useSecretKey(secretKey.key, admin)
            return admin
        } catch (e: Exception) {
            userRepository.delete(admin)
            throw e
        }
    }

    fun findUser(username: String): User {
        return userRepository.findByEmail(username) ?: userRepository.findByPhoneNumber(username)
        ?: throw UserNotFoundException("User not found")
    }

    private fun getAdmins(): List<User> = userRepository.findAllByRole(Role.ADMIN)
}
