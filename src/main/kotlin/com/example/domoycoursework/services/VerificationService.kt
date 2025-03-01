package com.example.domoycoursework.services

import com.example.domoycoursework.dto.VerificationRequestDto
import com.example.domoycoursework.enums.RequestStatus
import com.example.domoycoursework.enums.Role
import com.example.domoycoursework.enums.VerificationStatus
import com.example.domoycoursework.exceptions.InvalidUserData
import com.example.domoycoursework.exceptions.NotFoundException
import com.example.domoycoursework.exceptions.RequestAlreadyInWork
import com.example.domoycoursework.exceptions.UserAlreadyVerified
import com.example.domoycoursework.models.TSJRequest
import com.example.domoycoursework.models.VerificationRequest
import com.example.domoycoursework.repos.TSJRequestRepository
import com.example.domoycoursework.repos.VerificationRequestRepository
import org.springframework.stereotype.Service

@Service
class VerificationService(
    private val userService: UserService,
    private val adminService: AdminService,
    private val jwtService: JwtService,
    private val verificationRequestRepository: VerificationRequestRepository,
    private val houseAndFlatService: HouseAndFlatService,
    private val tsjRequestRepository: TSJRequestRepository
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
            it.address = verificationRequestDto.address
            it.flatNumber = verificationRequestDto.flatNumber
            verificationRequestRepository.save(it)
        } ?: VerificationRequest(
            id = 0,
            user = user,
            status = RequestStatus.PENDING,
            firstName = verificationRequestDto.firstName,
            lastName = verificationRequestDto.lastName,
            cadastralNumber = verificationRequestDto.cadastralNumber,
            address = verificationRequestDto.address,
            flatNumber = verificationRequestDto.flatNumber
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
        userService.setAdditionalUserData(verificationRequest, houseAndFlatService.createFlat(verificationRequest))
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

    fun processTSJRequest(token: String): TSJRequest {
        val user = userService.loadUserByEmail(jwtService.getUsername(jwtService.extractToken(token)))
            ?.takeIf { it.verificationStatus == VerificationStatus.VERIFIED }
            ?.also {
                when {
                    it.role == Role.ROLE_ADMIN -> throw InvalidUserData("User is admin")
                    it.role == Role.ROLE_TSJ -> throw InvalidUserData("User is already TSJ")
                }
            }
            ?: throw InvalidUserData("User not verified")
        return tsjRequestRepository.findTSJRequestByUser(user)?.let {
            if (it.status != RequestStatus.DECLINED) {
                throw RequestAlreadyInWork("TSJ request is not declined")
            }
            it.status = RequestStatus.PENDING
            tsjRequestRepository.save(it)
        } ?: TSJRequest(
            id = 0,
            user = user,
            status = RequestStatus.PENDING,
        ).also { tsjRequestRepository.save(it) }
    }

    fun approveTSJRequest(id: Long): TSJRequest {
        val tsjRequest = tsjRequestRepository.findTSJRequestById(id)?.let {
            if (it.status == RequestStatus.PENDING) {
                it.user.role = Role.ROLE_TSJ
                it.status = RequestStatus.ACCEPTED
                userService.changeRole(it.user, Role.ROLE_TSJ)
                it
            } else {
                throw RequestAlreadyInWork("TSJ request is not pending")
            }
        } ?: throw NotFoundException("TSJ request not found")
        tsjRequest.status = RequestStatus.ACCEPTED
        return tsjRequestRepository.save(tsjRequest)
    }

    fun declineTSJRequest(id: Long): TSJRequest {
        return tsjRequestRepository.findTSJRequestById(id)?.let {
            if (it.status == RequestStatus.PENDING) {
                it.status = RequestStatus.DECLINED
                tsjRequestRepository.save(it)
            } else {
                throw RequestAlreadyInWork("TSJ request is not pending")
            }
        } ?: throw NotFoundException("TSJ request not found")
    }


}