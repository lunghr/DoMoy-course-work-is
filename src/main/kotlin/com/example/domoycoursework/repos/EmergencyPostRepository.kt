package com.example.domoycoursework.repos

import com.example.domoycoursework.models.EmergencyPost
import org.springframework.data.jpa.repository.JpaRepository

interface EmergencyPostRepository : JpaRepository<EmergencyPost, Long>{
}