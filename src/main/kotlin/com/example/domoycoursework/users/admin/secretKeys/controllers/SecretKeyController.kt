package com.example.domoycoursework.users.admin.secretKeys.controllers

import com.example.domoycoursework.users.admin.secretKeys.services.SecretKeyService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/admin/secret-keys")
@CrossOrigin(origins = ["*"])
@Tag(name = "Secret Keys Creation")
class SecretKeyController(
    private val secretKeyService: SecretKeyService
) {

    @PostMapping("/create")
    fun createSecretKey(): String {
        return secretKeyService.createSecretKey()
    }
}