package com.example.domoycoursework.exceptions.controllers


import com.example.domoycoursework.users.admin.secretKeys.models.SecretKey
import com.example.domoycoursework.exceptions.models.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(e: UserNotFoundException): ResponseEntity<String> {
        return ResponseEntity.status(404).body(e.message)
    }

    @ExceptionHandler(EmailAlreadyInUse::class)
    fun handleEmailAlreadyInUse(e: EmailAlreadyInUse): ResponseEntity<String> {
        return ResponseEntity.status(409).body(e.message)
    }

    @ExceptionHandler(PhoneNumberAlreadyInUse::class)
    fun handlePhoneNumberAlreadyInUse(e: PhoneNumberAlreadyInUse): ResponseEntity<String> {
        return ResponseEntity.status(409).body(e.message)
    }

    @ExceptionHandler(InvalidSecretKey::class)
    fun handleInvalidSecretKey(e: InvalidSecretKey): ResponseEntity<String> {
        return ResponseEntity.status(401).body(e.message)
    }
}
