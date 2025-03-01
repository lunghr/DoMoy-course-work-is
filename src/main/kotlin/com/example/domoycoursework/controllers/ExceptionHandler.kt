package com.example.domoycoursework.controllers


import com.example.domoycoursework.exceptions.*
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

    @ExceptionHandler(UserAlreadyVerified :: class)
    fun handleUserAlreadyVerified(e: UserAlreadyVerified): ResponseEntity<String> {
        return ResponseEntity.status(409).body(e.message)
    }

    @ExceptionHandler(RequestAlreadyInWork::class)
    fun handleRequestAlreadyInWork(e: RequestAlreadyInWork): ResponseEntity<String> {
        return ResponseEntity.status(409).body(e.message)
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException): ResponseEntity<String> {
        return ResponseEntity.status(404).body(e.message)
    }

    @ExceptionHandler(InvalidUserData::class)
    fun handleInvalidUserData(e: InvalidUserData): ResponseEntity<String> {
        return ResponseEntity.status(400).body(e.message)
    }
}
