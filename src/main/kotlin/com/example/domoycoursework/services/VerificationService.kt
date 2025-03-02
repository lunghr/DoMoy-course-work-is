package com.example.domoycoursework.services

import com.example.domoycoursework.dto.TSJResponseDto
import com.example.domoycoursework.dto.VerificationRequestDto
import com.example.domoycoursework.dto.VerificationResponseDto
import com.example.domoycoursework.enums.RequestStatus
import com.example.domoycoursework.enums.Role
import com.example.domoycoursework.enums.VerificationStatus
import com.example.domoycoursework.exceptions.InvalidUserDataException
import com.example.domoycoursework.exceptions.NotFoundException
import com.example.domoycoursework.exceptions.RequestAlreadyInWorkException
import com.example.domoycoursework.exceptions.UserAlreadyVerifiedException
import com.example.domoycoursework.models.TSJRequest
import com.example.domoycoursework.models.VerificationRequest
import com.example.domoycoursework.repos.TSJRequestRepository
import com.example.domoycoursework.repos.VerificationRequestRepository
import org.springframework.stereotype.Service

@Service
class VerificationService(
    private val userService: UserService,
    private val jwtService: JwtService,
    private val verificationRequestRepository: VerificationRequestRepository,
    private val houseAndFlatService: HouseAndFlatService,
    private val tsjRequestRepository: TSJRequestRepository
) {

    fun processVerificationRequest(
        verificationRequestDto: VerificationRequestDto,
        token: String
    ): VerificationResponseDto {
        val user = userService.loadUserByEmail(jwtService.getUsername(jwtService.extractToken(token)))
            ?.takeIf { it.verificationStatus != VerificationStatus.VERIFIED }
            ?: throw UserAlreadyVerifiedException("User already verified")
        return createVerificationResponseDto(verificationRequestRepository.findVerificationRequestByUser(user)?.let {
            if (it.status != RequestStatus.DECLINED) {
                throw RequestAlreadyInWorkException("Verification request is not declined")
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
        ).also { verificationRequestRepository.save(it) })
    }

    fun approveVerificationRequest(id: Long): VerificationResponseDto {
        val verificationRequest = verificationRequestRepository.findVerificationRequestById(id)?.let {
            if (it.status == RequestStatus.PENDING) {
                it
            } else {
                throw RequestAlreadyInWorkException("Verification request is not pending")
            }
        } ?: throw NotFoundException("Verification request not found")
        verificationRequest.status = RequestStatus.ACCEPTED
        userService.setAdditionalUserData(verificationRequest, houseAndFlatService.createFlat(verificationRequest))
        return createVerificationResponseDto(verificationRequestRepository.save(verificationRequest))
    }

    fun declineVerificationRequest(id: Long): VerificationResponseDto {
        return verificationRequestRepository.findVerificationRequestById(id)?.let {
            if (it.status == RequestStatus.PENDING) {
                it.status = RequestStatus.DECLINED
                createVerificationResponseDto(verificationRequestRepository.save(it))
            } else {
                throw RequestAlreadyInWorkException("Verification request is not pending")
            }
        } ?: throw NotFoundException("Verification request not found")
    }

    fun processTSJRequest(token: String): TSJResponseDto {
        val user = userService.loadUserByEmail(jwtService.getUsername(jwtService.extractToken(token)))
            ?.takeIf { it.verificationStatus == VerificationStatus.VERIFIED }
            ?.also {
                when {
                    it.role == Role.ROLE_ADMIN -> throw InvalidUserDataException("User is admin")
                    it.role == Role.ROLE_TSJ -> throw InvalidUserDataException("User is already TSJ")
                }
            }
            ?: throw InvalidUserDataException("User not verified")
        return createTSJResponseDto(tsjRequestRepository.findTSJRequestByUser(user)?.let {
            if (it.status != RequestStatus.DECLINED) {
                throw RequestAlreadyInWorkException("TSJ request is not declined")
            }
            it.status = RequestStatus.PENDING
            tsjRequestRepository.save(it)
        } ?: TSJRequest(
            id = 0,
            user = user,
            status = RequestStatus.PENDING,
        ).also { tsjRequestRepository.save(it) })
    }

    fun approveTSJRequest(id: Long): TSJResponseDto {
        val tsjRequest = tsjRequestRepository.findTSJRequestById(id)?.let {
            if (it.status == RequestStatus.PENDING) {
                it.user.role = Role.ROLE_TSJ
                it.status = RequestStatus.ACCEPTED
                userService.changeRole(it.user, Role.ROLE_TSJ)
                it
            } else {
                throw RequestAlreadyInWorkException("TSJ request is not pending")
            }
        } ?: throw NotFoundException("TSJ request not found")
        tsjRequest.status = RequestStatus.ACCEPTED
        return createTSJResponseDto(tsjRequestRepository.save(tsjRequest))
    }

    fun declineTSJRequest(id: Long): TSJResponseDto {
        return tsjRequestRepository.findTSJRequestById(id)?.let {
            if (it.status == RequestStatus.PENDING) {
                it.status = RequestStatus.DECLINED
                createTSJResponseDto(tsjRequestRepository.save(it))
            } else {
                throw RequestAlreadyInWorkException("TSJ request is not pending")
            }
        } ?: throw NotFoundException("TSJ request not found")
    }

    fun createVerificationResponseDto(verificationRequest: VerificationRequest): VerificationResponseDto {
        return VerificationResponseDto(
            id = verificationRequest.id,
            userId = verificationRequest.user.id,
            status = verificationRequest.status,
            firstName = verificationRequest.firstName,
            lastName = verificationRequest.lastName,
            cadastralNumber = verificationRequest.cadastralNumber,
            address = verificationRequest.address,
            flatNumber = verificationRequest.flatNumber,
        )
    }

    fun createTSJResponseDto(tsjRequest: TSJRequest): TSJResponseDto {
        return TSJResponseDto(
            id = tsjRequest.id,
            userId = tsjRequest.user.id,
            status = tsjRequest.status,
        )
    }


}