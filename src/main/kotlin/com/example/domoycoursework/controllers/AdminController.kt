package com.example.domoycoursework.controllers

import com.example.domoycoursework.dto.EmergencyNotificationDto
import com.example.domoycoursework.dto.HouseCreationRequestDto
import com.example.domoycoursework.models.*
import com.example.domoycoursework.services.*
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

class AdminController(
    private val secretKeyService: SecretKeyService
//    private val houseAndFlatService: HouseAndFlatService,
//    private val emergencyNotificationService: EmergencyNotificationService,
) {
//    @PostMapping("/houses/create")
//    fun createHouse(@RequestBody houseCreationRequestDto: HouseCreationRequestDto): House {
//        return houseAndFlatService.createHouse(houseCreationRequestDto.address)
//    }
//
//    @GetMapping("/houses/{houseId}/flats")
//    fun getFlatsByHouse(@PathVariable houseId: Int): List<Int> {
//        return houseAndFlatService.getFlatsByHouse(houseId)
//    }
//
//    @PostMapping("/emergency-notification/create")
//    fun createEmergencyNotification(
//        @RequestBody emergencyNotificationDto: EmergencyNotificationDto,
//        @RequestHeader("Authorization") token: String
//    ): EmergencyPost {
//        return emergencyNotificationService.createEmergencyNotification(emergencyNotificationDto, token)
//    }

    @PostMapping("/key")
    fun createKey() {
        secretKeyService.createSecretKey()
    }

    @GetMapping("/key")
    fun getAvailableKeys(): List<SecretKey> {
        return secretKeyService.getAllAvailableKeys()
    }
}