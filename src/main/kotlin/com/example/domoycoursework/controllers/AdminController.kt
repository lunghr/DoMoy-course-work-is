package com.example.domoycoursework.controllers

import com.example.domoycoursework.dto.HouseDto
import com.example.domoycoursework.models.Flat
import com.example.domoycoursework.models.House
import com.example.domoycoursework.models.SecretKey
import com.example.domoycoursework.services.ResidentialComplexService
import com.example.domoycoursework.services.SecretKeyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = ["*"])
class AdminController(
    private val secretKeyService: SecretKeyService,
    private val residentialComplexService: ResidentialComplexService
) {

    @PostMapping("/houses")
    fun createHouse(
        @RequestBody houseDto: HouseDto
    ): ResponseEntity<String> {
        residentialComplexService.createHouse(houseDto)
        return ResponseEntity.status(HttpStatus.CREATED).body("House created successfully")
    }

    @GetMapping("/houses/{id}")
    fun getHouse(@PathVariable id: Long): ResponseEntity<House> {
        return ResponseEntity.ok(residentialComplexService.getHouseById(id))
    }

    @GetMapping("/houses/{id}/flats")
    fun getFlatsByHouse(@PathVariable id: Long): ResponseEntity<List<Flat>> {
        return ResponseEntity.ok(residentialComplexService.getFlatsByHouse(id))
    }

    @PostMapping("/keys")
    fun createKey(): ResponseEntity<String> {
        secretKeyService.createSecretKey()
        return ResponseEntity.status(HttpStatus.CREATED).body("Secret key created")
    }

    @GetMapping("/keys/available")
    fun getAvailableKeys(): ResponseEntity<List<SecretKey>> {
        return ResponseEntity.ok(secretKeyService.getAllAvailableKeys())
    }
}