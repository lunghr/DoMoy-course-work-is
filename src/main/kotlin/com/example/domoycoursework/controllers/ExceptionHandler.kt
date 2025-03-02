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

    @ExceptionHandler(EmailAlreadyInUseException::class)
    fun handleEmailAlreadyInUse(e: EmailAlreadyInUseException): ResponseEntity<String> {
        return ResponseEntity.status(409).body(e.message)
    }

    @ExceptionHandler(PhoneNumberAlreadyInUseException::class)
    fun handlePhoneNumberAlreadyInUse(e: PhoneNumberAlreadyInUseException): ResponseEntity<String> {
        return ResponseEntity.status(409).body(e.message)
    }

    @ExceptionHandler(InvalidSecretKeyException::class)
    fun handleInvalidSecretKey(e: InvalidSecretKeyException): ResponseEntity<String> {
        return ResponseEntity.status(401).body(e.message)
    }

    @ExceptionHandler(UserAlreadyVerifiedException :: class)
    fun handleUserAlreadyVerified(e: UserAlreadyVerifiedException): ResponseEntity<String> {
        return ResponseEntity.status(409).body(e.message)
    }

    @ExceptionHandler(RequestAlreadyInWorkException::class)
    fun handleRequestAlreadyInWork(e: RequestAlreadyInWorkException): ResponseEntity<String> {
        return ResponseEntity.status(409).body(e.message)
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException): ResponseEntity<String> {
        return ResponseEntity.status(404).body(e.message)
    }

    @ExceptionHandler(InvalidUserDataException::class)
    fun handleInvalidUserData(e: InvalidUserDataException): ResponseEntity<String> {
        return ResponseEntity.status(400).body(e.message)
    }

    @ExceptionHandler(FileException::class)
    fun handleFileUploadError(e: FileException): ResponseEntity<String> {
        return ResponseEntity.status(400).body(e.message)
    }

    @ExceptionHandler(NoPermissionException::class)
    fun handleNoPermissionException(e: NoPermissionException): ResponseEntity<String> {
        return ResponseEntity.status(403).body(e.message)
    }
}
