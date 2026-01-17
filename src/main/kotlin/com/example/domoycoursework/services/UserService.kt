package com.example.domoycoursework.services

import com.example.domoycoursework.models.enums.Role
import com.example.domoycoursework.models.Flat
import com.example.domoycoursework.models.User
import com.example.domoycoursework.models.VerificationRequest
import com.example.domoycoursework.repos.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private var userRepository: UserRepository
) {
    fun createUser(user: User): User =
        userRepository.findUserByEmail(user.email)?.let { throw Exception("User already exists") }
            ?: userRepository.save(user)

    fun loadUserByPhoneNumber(phoneNumber: String): User? {
        return userRepository.findUserByPhoneNumber(phoneNumber)
    }

    fun loadUserByEmail(email: String): User? {
        return userRepository.findUserByEmail(email)
    }

    fun setAdditionalUserData(verificationRequest: VerificationRequest, flat: Flat): User {
        val user = userRepository.findUserById(verificationRequest.userId)
            ?: throw Exception("User not found")
        val chatName = "${verificationRequest.firstName} ${verificationRequest.lastName}, ${flat.flatNumber}"
        return userRepository.setAdditionalUserData(user, verificationRequest, flat, chatName)

    }

    fun changeRole(id: Int, role: Role): User {
        return userRepository.changeRole(id, role)
    }


}
