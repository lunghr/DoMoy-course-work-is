package com.example.domoycoursework.users.admin.repos

import com.example.domoycoursework.users.admin.models.Admin
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AdminRepository : JpaRepository<Admin, Long> {
    fun findByEmail(email: String): Admin?
    fun findByPhoneNumber(phoneNumber: String): Admin?


}
