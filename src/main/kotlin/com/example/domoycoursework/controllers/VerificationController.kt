package com.example.domoycoursework.controllers

import com.example.domoycoursework.dto.VerificationRequestDto
import com.example.domoycoursework.models.TSJRequest
import com.example.domoycoursework.models.VerificationRequest
import com.example.domoycoursework.services.VerificationService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/verification")
@CrossOrigin(origins = ["*"])
@Tag(name = "User data verification")
class VerificationController (
    private var verificationService: VerificationService
){

    @PostMapping("/request")
    fun processVerificationRequest(@RequestHeader("Authorization") token: String, @Valid @RequestBody verificationRequestDto: VerificationRequestDto): VerificationRequest {
        return verificationService.processVerificationRequest(verificationRequestDto, token)
    }

    @PostMapping("admin/approve/{id}")
    fun approveVerificationRequest(@PathVariable id: Long): VerificationRequest {
       return verificationService.approveVerificationRequest(id)
    }

    @PostMapping("admin/decline/{id}")
    fun rejectVerificationRequest(@PathVariable id: Long): VerificationRequest {
        return verificationService.declineVerificationRequest(id)
    }

    @PostMapping("/TSJ-request")
    fun processTSJRequest(@RequestHeader("Authorization") token: String): TSJRequest {
        return verificationService.processTSJRequest(token)
    }

    @PostMapping("admin/approve-TSJ/{id}")
    fun approveTSJRequest(@PathVariable id: Long): TSJRequest {
        return verificationService.approveTSJRequest(id)
    }

    @PostMapping("admin/decline-TSJ/{id}")
    fun rejectTSJRequest(@PathVariable id: Long): TSJRequest {
        return verificationService.declineTSJRequest(id)
    }

}