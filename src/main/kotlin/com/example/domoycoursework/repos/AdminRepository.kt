package com.example.domoycoursework.repos

import com.example.domoycoursework.models.Admin
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AdminRepository : JpaRepository<Admin, Long> {
    fun findByEmail(email: String): Admin?
    fun findByPhoneNumber(phoneNumber: String): Admin?


}
