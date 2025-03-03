package com.example.domoycoursework.repos

import com.example.domoycoursework.models.EmergencyNotification
import org.springframework.data.jpa.repository.JpaRepository

interface EmergencyNotificationRepository : JpaRepository<EmergencyNotification, Long>{

}