package com.example.domoycoursework.controllers

import com.example.domoycoursework.dto.EmergencyNotificationDto
import com.example.domoycoursework.dto.HouseCreationRequestDto
import com.example.domoycoursework.models.EmergencyNotification
import com.example.domoycoursework.models.Flat
import com.example.domoycoursework.models.House
import com.example.domoycoursework.services.EmergencyNotificationService
import com.example.domoycoursework.services.HouseAndFlatService
import com.example.domoycoursework.services.SecretKeyService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = ["*"])
@Tag(name = "Secret Keys Creation")
class AdminController(
    private val secretKeyService: SecretKeyService,
    private val houseAndFlatService: HouseAndFlatService,
    private val emergencyNotificationService: EmergencyNotificationService
) {

    @PostMapping("/secret-keys/create")
    fun createSecretKey(): String {
        return secretKeyService.createSecretKey()
    }

    @PostMapping("/houses/create")
    fun createHouse(@RequestBody houseCreationRequestDto: HouseCreationRequestDto): House {
        return houseAndFlatService.createHouse(houseCreationRequestDto.address)
    }

    @GetMapping("/houses/{houseId}/flats")
    fun getFlatsByHouse(@PathVariable houseId: Long): List<Int> {
        return houseAndFlatService.getFlatsByHouse(houseId)
    }

    @PostMapping("/emergency-notification/create")
    fun createEmergencyNotification(
        @RequestBody emergencyNotificationDto: EmergencyNotificationDto,
        @RequestHeader("Authorization") token: String
    ): EmergencyNotification {
        return emergencyNotificationService.createEmergencyNotification(emergencyNotificationDto, token)
    }
}