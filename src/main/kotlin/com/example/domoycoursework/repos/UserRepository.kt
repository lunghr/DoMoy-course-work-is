package com.example.domoycoursework.repos

import com.example.domoycoursework.models.User
import org.springframework.data.jpa.repository.JpaRepository


interface UserRepository : JpaRepository<User, Long>{
    fun findByEmail(email: String): User?
    fun findByPhoneNumber(phoneNumber: String): User?
}