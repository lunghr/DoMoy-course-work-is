package com.example.domoycoursework.services

import com.example.domoycoursework.dto.VerificationRequestDto
import com.example.domoycoursework.enums.RequestStatus
import com.example.domoycoursework.enums.VerificationStatus
import com.example.domoycoursework.exceptions.NotFoundException
import com.example.domoycoursework.exceptions.RequestAlreadyInWork
import com.example.domoycoursework.exceptions.UserAlreadyVerified
import com.example.domoycoursework.models.VerificationRequest
import com.example.domoycoursework.repos.VerificationRequestRepository
import org.springframework.stereotype.Service

@Service
class VerificationService(
    private val userService: UserService,
    private val adminService: AdminService,
    private val jwtService: JwtService,
    private val verificationRequestRepository: VerificationRequestRepository
) {

    fun processVerificationRequest(verificationRequestDto: VerificationRequestDto, token: String): VerificationRequest {
        val user = userService.loadUserByEmail(jwtService.getUsername(jwtService.extractToken(token)))
            ?.takeIf { it.verificationStatus != VerificationStatus.VERIFIED }
            ?: throw UserAlreadyVerified("User already verified")
        return verificationRequestRepository.findVerificationRequestByUser(user)?.let {
            if (it.status != RequestStatus.DECLINED) {
                throw RequestAlreadyInWork("Verification request is not declined")
            }
            it.status = RequestStatus.PENDING
            it.firstName = verificationRequestDto.firstName
            it.lastName = verificationRequestDto.lastName
            it.cadastralNumber = verificationRequestDto.cadastralNumber
            verificationRequestRepository.save(it)
        } ?: VerificationRequest(
            id = 0,
            user = user,
            status = RequestStatus.PENDING,
            firstName = verificationRequestDto.firstName,
            lastName = verificationRequestDto.lastName,
            cadastralNumber = verificationRequestDto.cadastralNumber
        ).also { verificationRequestRepository.save(it) }
    }

    fun approveVerificationRequest(id: Long): VerificationRequest {
        val verificationRequest = verificationRequestRepository.findVerificationRequestById(id)?.let {
            if (it.status == RequestStatus.PENDING) {
                it
            } else {
                throw RequestAlreadyInWork("Verification request is not pending")
            }
        } ?: throw NotFoundException("Verification request not found")
        verificationRequest.status = RequestStatus.ACCEPTED
        userService.setAdditionalUserData(verificationRequest)
        return verificationRequest
    }

    fun declineVerificationRequest(id: Long): VerificationRequest {
        return verificationRequestRepository.findVerificationRequestById(id)?.let {
            if (it.status == RequestStatus.PENDING) {
                it.status = RequestStatus.DECLINED
                verificationRequestRepository.save(it)
            } else {
                throw RequestAlreadyInWork("Verification request is not pending")
            }
        } ?: throw NotFoundException("Verification request not found")
    }


}