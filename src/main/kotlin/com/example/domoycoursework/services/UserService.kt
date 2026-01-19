package com.example.domoycoursework.services

import com.example.domoycoursework.exceptions.UserNotFoundException
import com.example.domoycoursework.models.User
import com.example.domoycoursework.repos.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private var userRepository: UserRepository
) {
    fun createUser(user: User): User =
        userRepository.findByEmail(user.email)?.let { throw Exception("User already exists") }
            ?: userRepository.save(user)

   fun findUser(username: String): User {
        return userRepository.findByEmail(username) ?: userRepository.findByPhoneNumber(username)
        ?: throw UserNotFoundException("User not found")
    }
}
