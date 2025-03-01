package com.example.domoycoursework.repos

import com.example.domoycoursework.enums.RequestStatus

import com.example.domoycoursework.models.User
import com.example.domoycoursework.models.VerificationRequest
import org.springframework.data.jpa.repository.JpaRepository

interface VerificationRequestRepository : JpaRepository <VerificationRequest, Long> {
    fun findVerificationRequestById(id: Long): VerificationRequest?
    fun findVerificationRequestByUser(user: User): VerificationRequest?

}