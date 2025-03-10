package com.example.domoycoursework.repos

import com.example.domoycoursework.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findUserById(id: Long): User?
    fun findUserByEmail(email: String): User?
    fun findUserByPhoneNumber(phoneNumber: String): User?
}
