package com.example.domoycoursework.services

import com.example.domoycoursework.enums.Role
import com.example.domoycoursework.enums.VerificationStatus
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
        return userRepository.save(user.apply {
            this.verificationStatus = VerificationStatus.VERIFIED
            this.firstName = verificationRequest.firstName
            this.lastName = verificationRequest.lastName
            this.flatId = flat.id
            this.chatName = "${this.firstName} ${this.lastName}, ${flat.flatNumber}"
        })

    }

    fun changeRole(user: User, role: Role): User {
        return userRepository.save(user.apply {
            this.role = role
        })
    }




}
