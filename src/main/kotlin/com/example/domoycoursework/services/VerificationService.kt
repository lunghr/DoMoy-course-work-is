//package com.example.domoycoursework.services
//
//import com.example.domoycoursework.dto.TSJResponseDto
//import com.example.domoycoursework.dto.VerificationRequestDto
//import com.example.domoycoursework.dto.VerificationResponseDto
//import com.example.domoycoursework.models.enums.RequestStatus
//import com.example.domoycoursework.models.enums.Role
//import com.example.domoycoursework.models.enums.VerificationStatus
//import com.example.domoycoursework.exceptions.InvalidUserDataException
//import com.example.domoycoursework.exceptions.NotFoundException
//import com.example.domoycoursework.exceptions.RequestAlreadyInWorkException
//import com.example.domoycoursework.exceptions.UserAlreadyVerifiedException
//import com.example.domoycoursework.models.TSJRequest
//import com.example.domoycoursework.models.VerificationRequest
//import com.example.domoycoursework.repos.TSJRequestRepository
//import com.example.domoycoursework.repos.VerificationRequestRepository
//import org.springframework.stereotype.Service
//
//@Service
//class VerificationService(
//    private val userService: UserService,
//    private val jwtService: JwtService,
//    private val verificationRequestRepository: VerificationRequestRepository,
//    private val houseAndFlatService: HouseAndFlatService,
//    private val tsjRequestRepository: TSJRequestRepository
//) {
//
//    fun processVerificationRequest(
//        verificationRequestDto: VerificationRequestDto,
//        token: String
//    ): VerificationResponseDto {
//        val user = userService.loadUserByEmail(jwtService.getUsername(jwtService.extractToken(token)))
//            ?.takeIf { it.verificationStatus != VerificationStatus.VERIFIED }
//            ?: throw UserAlreadyVerifiedException("User already verified")
//        return createVerificationResponseDto(verificationRequestRepository.findVerificationRequestByUser(user)?.let {
//            if (it.status != RequestStatus.DECLINED) {
//                throw RequestAlreadyInWorkException("Verification request is not declined")
//            }
//            verificationRequestRepository.save(verificationRequestDto, it.id)
//        } ?: verificationRequestRepository.createVerificationRequest(verificationRequestDto, user))
//    }
//
//    fun approveVerificationRequest(id: Int): VerificationResponseDto {
//        val verificationRequest = verificationRequestRepository.findVerificationRequestById(id)?.let {
//            if (it.status == RequestStatus.PENDING) {
//                it
//            } else {
//                throw RequestAlreadyInWorkException("Verification request is not pending")
//            }
//        } ?: throw NotFoundException("Verification request not found")
//        userService.setAdditionalUserData(verificationRequest, houseAndFlatService.createFlat(verificationRequest))
//        return createVerificationResponseDto(
//            verificationRequestRepository.changeVerificationRequestStatus(
//                id,
//                RequestStatus.ACCEPTED
//            )
//        )
//    }
//
//    fun declineVerificationRequest(id: Int): VerificationResponseDto {
//        return verificationRequestRepository.findVerificationRequestById(id)?.let {
//            if (it.status == RequestStatus.PENDING) {
//                createVerificationResponseDto(
//                    verificationRequestRepository.changeVerificationRequestStatus(
//                        id,
//                        RequestStatus.DECLINED
//                    )
//                )
//            } else {
//                throw RequestAlreadyInWorkException("Verification request is not pending")
//            }
//        } ?: throw NotFoundException("Verification request not found")
//    }
//
//    fun processTSJRequest(token: String): TSJResponseDto {
//        val user = userService.loadUserByEmail(jwtService.getUsername(jwtService.extractToken(token)))
//            ?.takeIf { it.verificationStatus == VerificationStatus.VERIFIED }
//            ?.also {
//                when {
//                    it.role == Role.ROLE_ADMIN -> throw InvalidUserDataException("User is admin")
//                    it.role == Role.ROLE_TSJ -> throw InvalidUserDataException("User is already TSJ")
//                }
//            }
//            ?: throw InvalidUserDataException("User not verified")
//        return createTSJResponseDto(tsjRequestRepository.findTSJRequestByUser(user)?.let {
//            if (it.status != RequestStatus.DECLINED) {
//                throw RequestAlreadyInWorkException("TSJ request is not declined")
//            }
//            tsjRequestRepository.changeTSJRequestStatus(it.id, RequestStatus.PENDING)
//        } ?: tsjRequestRepository.createTSJRequest(user.id))
//    }
//
//    fun approveTSJRequest(id: Int): TSJResponseDto {
//        tsjRequestRepository.findTSJRequestById(id)?.let {
//            if (it.status == RequestStatus.PENDING) {
//                userService.changeRole(it.userId, Role.ROLE_TSJ)
//                it
//            } else {
//                throw RequestAlreadyInWorkException("TSJ request is not pending")
//            }
//        } ?: throw NotFoundException("TSJ request not found")
//        return createTSJResponseDto(tsjRequestRepository.changeTSJRequestStatus(id, RequestStatus.ACCEPTED))
//    }
//
//    fun declineTSJRequest(id: Int): TSJResponseDto {
//        return tsjRequestRepository.findTSJRequestById(id)?.let {
//            if (it.status == RequestStatus.PENDING) {
//                createTSJResponseDto(tsjRequestRepository.changeTSJRequestStatus(id, RequestStatus.DECLINED))
//            } else {
//                throw RequestAlreadyInWorkException("TSJ request is not pending")
//            }
//        } ?: throw NotFoundException("TSJ request not found")
//    }
//
//    fun createVerificationResponseDto(verificationRequest: VerificationRequest): VerificationResponseDto {
//        return VerificationResponseDto(
//            id = verificationRequest.id,
//            userId = verificationRequest.userId,
//            status = verificationRequest.status,
//            firstName = verificationRequest.firstName,
//            lastName = verificationRequest.lastName,
//            cadastralNumber = verificationRequest.cadastralNumber,
//            address = verificationRequest.address,
//            flatNumber = verificationRequest.flatNumber,
//        )
//    }
//
//    fun createTSJResponseDto(tsjRequest: TSJRequest): TSJResponseDto {
//        return TSJResponseDto(
//            id = tsjRequest.id,
//            userId = tsjRequest.userId,
//            status = tsjRequest.status,
//        )
//    }
//
//    fun getAllVerificationRequests(): List<VerificationRequest> {
//        return verificationRequestRepository.findAll()
//    }
//
//    fun getAllTSJRequests(): List<TSJRequest> {
//        return tsjRequestRepository.findALl()
//    }
//
//    fun getVerificationRequestStatus(token: String): RequestStatus {
//        val user = userService.loadUserByEmail(jwtService.getUsername(jwtService.extractToken(token)))
//            ?: throw NotFoundException("User not found")
//        return verificationRequestRepository.findVerificationRequestByUser(user)?.status
//            ?: throw NotFoundException("Verification request not found")
//    }
//
//    fun getTSJRequestStatus(token: String): RequestStatus {
//        val user = userService.loadUserByEmail(jwtService.getUsername(jwtService.extractToken(token)))
//            ?: throw NotFoundException("User not found")
//        return tsjRequestRepository.findTSJRequestByUser(user)?.status
//            ?: throw NotFoundException("TSJ request not found")
//    }
//
//
//}