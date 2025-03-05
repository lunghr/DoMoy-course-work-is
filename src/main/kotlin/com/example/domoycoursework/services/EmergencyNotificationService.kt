package com.example.domoycoursework.services

import com.example.domoycoursework.dto.EmergencyNotificationDto
import com.example.domoycoursework.exceptions.NoPermissionException
import com.example.domoycoursework.exceptions.NotFoundException
import com.example.domoycoursework.models.EmergencyNotification
import com.example.domoycoursework.repos.EmergencyNotificationRepository
import org.springframework.stereotype.Service

@Service
class EmergencyNotificationService(
    private var emergencyNotificationRepository: EmergencyNotificationRepository,
    private var houseAndFlatService: HouseAndFlatService,
    private var jwtService: JwtService,
    private var adminService: AdminService
) {

    fun createEmergencyNotification(
        emergencyNotificationDto: EmergencyNotificationDto,
        token: String
    ): EmergencyNotification {
        return adminService.loadAdminByEmail(jwtService.getUsername(jwtService.extractToken(token)))?.let { admin ->
            houseAndFlatService.findHouseById(emergencyNotificationDto.houseId)?.let {
                emergencyNotificationRepository.save(emergencyNotificationDto, admin.id)
            } ?: throw NotFoundException("House not found")
        } ?: throw NotFoundException("Admin not found")
    }

    fun sendEmergencyNotification(emergencyNotification: EmergencyNotification) {
        //TODO send notification to all users in the house with websockets idk how to id so
    }

}