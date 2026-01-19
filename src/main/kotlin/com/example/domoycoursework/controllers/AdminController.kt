package com.example.domoycoursework.controllers

import com.example.domoycoursework.dto.HouseDto
import com.example.domoycoursework.models.*
import com.example.domoycoursework.services.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = ["*"])

class AdminController(
    private val secretKeyService: SecretKeyService,
    private val residentialComplexService: ResidentialComplexService,
) {

    @PostMapping("/house")
    fun createHouse(@RequestBody houseDto: HouseDto): ResponseEntity<Any> {
        residentialComplexService.createHouse(houseDto)
        return ResponseEntity.status(201).body("House created successfully")
    }

    @GetMapping("/house/{id}")
    fun getHouseById(@PathVariable id: Long): House {
        return residentialComplexService.getHouseById(id)
    }

    @GetMapping("/house/{id}/flat")
    fun getFlatsByHouse(@PathVariable id: Long): List<Flat> {
        return residentialComplexService.getFlatsByHouse(id)
    }

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