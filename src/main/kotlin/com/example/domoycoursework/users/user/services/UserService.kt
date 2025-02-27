package com.example.domoycoursework.users.user.services

import com.example.domoycoursework.users.user.models.User
import com.example.domoycoursework.users.user.repos.UserRepository
import com.example.domoycoursework.exceptions.models.UserNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService {
    @Autowired
    private lateinit var userRepository: UserRepository

//    fun createUser(user: User): User =
//        userRepository.findUserByEmail(user.email)?.let { throw Exception("User already exists") }
//            ?: userRepository.save(user)
    @Transactional
    fun createUser(user: User): User {
        println("Trying to find user by email: ${user.email}")
        val existingUser = userRepository.findUserByEmail(user.email)
        if (existingUser != null) {
            println("User already exists: ${user.email}")
            throw Exception("User already exists")
        }
        println("Saving user: ${user.email}")
        return userRepository.save(user)
    }

//    fun getUserByEmail(email: String): User =
//        userRepository.findUserByEmail(email)

//    fun userDetailsService(): UserDetailsService = UserDetailsService { getUserByEmail(it) }

    fun getUserById(id: Long): User = userRepository.findUserById(id) ?: throw UserNotFoundException("User not found")

    fun findAll(): List<User> = userRepository.findAll()

    fun loadUserByPhoneNumber(phoneNumber: String): User? {
        return userRepository.findUserByPhoneNumber(phoneNumber)
    }

    fun loadUserByEmail(email: String): User? {
        return userRepository.findUserByEmail(email)
    }

}
