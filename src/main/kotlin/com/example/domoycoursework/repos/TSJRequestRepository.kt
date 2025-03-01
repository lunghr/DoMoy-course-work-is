package com.example.domoycoursework.repos

import com.example.domoycoursework.models.TSJRequest
import com.example.domoycoursework.models.User
import org.springframework.data.jpa.repository.JpaRepository

interface TSJRequestRepository: JpaRepository<TSJRequest, Long> {
    fun findTSJRequestByUser(user: User): TSJRequest?
    fun findTSJRequestById(id: Long): TSJRequest?
}

