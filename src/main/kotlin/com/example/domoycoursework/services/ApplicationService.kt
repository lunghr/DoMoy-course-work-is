package com.example.domoycoursework.services

import com.example.domoycoursework.dto.ApplicationRequestDto
import com.example.domoycoursework.dto.ApplicationResponseDto
import com.example.domoycoursework.dto.ApplicationResponsesHistoryDto
import com.example.domoycoursework.models.enums.TicketStatus
import com.example.domoycoursework.exceptions.NoPermissionException
import com.example.domoycoursework.exceptions.NotFoundException
import com.example.domoycoursework.exceptions.UserNotFoundException
import com.example.domoycoursework.models.Ticket
import com.example.domoycoursework.models.TicketComments
import com.example.domoycoursework.repos.ApplicationRepository
import com.example.domoycoursework.repos.ApplicationResponseRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.cdimascio.dotenv.Dotenv
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ApplicationService(
    private var applicationRepository: ApplicationRepository,
    private var applicationResponseRepository: ApplicationResponseRepository,
    private var userService: UserService,
    private var jwtService: JwtService,
    private var adminService: AdminService,
    private var fileService: FileService
) {
    //TODO: change to System.getenv("MINIO_BUCKET")
    val dotenv: Dotenv = Dotenv.load()
    val bucketName: String = "applicationimages"


    fun createApplication(
        applicationRequestDto: ApplicationRequestDto,
        token: String,
        files: List<MultipartFile>?
    ): Ticket {
        return userService.loadUserByEmail(jwtService.getUsername(jwtService.extractToken(token)))?.let {
            applicationRepository.createApplication(applicationRequestDto, it.id, files)
        } ?: throw UserNotFoundException("User not found")
    }

    fun createResponse(
        applicationResponseDto: ApplicationResponseDto,
        token: String,
        id: Int
    ): ApplicationResponsesHistoryDto {
        return adminService.loadAdminByEmail(jwtService.getUsername(jwtService.extractToken(token)))?.let { admin ->
            applicationRepository.findApplicationById(id)?.let { application ->
                if (application.status == TicketStatus.COMPLETED || application.status == TicketStatus.REJECTED) {
                    throw NoPermissionException("Application is already completed or rejected")
                }
                applicationRepository.updateApplicationStatus(
                    id,
                    TicketStatus.valueOf(applicationResponseDto.status)
                )
                applicationResponseRepository.createApplicationResponse(
                    applicationResponseDto,
                    admin.id,
                    id
                )
                return createHistory(application)
            } ?: throw NotFoundException("Application not found")
        } ?: throw NotFoundException("Admin not found")
    }

    fun getApplication(id: Int, token: String): Ticket {
        return applicationRepository.findApplicationById(id)?.let { application ->
            userService.loadUserByEmail(jwtService.getUsername(jwtService.extractToken(token)))?.let { user ->
                if (application.userId != user.id) {
                    throw UserNotFoundException("User is not the author of this application")
                }
                return application
            } ?: adminService.loadAdminByEmail(jwtService.getUsername(jwtService.extractToken(token)))?.let {
                return application
            } ?: throw UserNotFoundException("User not found")
        } ?: throw NotFoundException("Application not found")
    }

    fun convertRequestToDto(applicationDto: String): ApplicationRequestDto {
        return jacksonObjectMapper().readValue(applicationDto)
    }

    fun createHistory(application: Ticket): ApplicationResponsesHistoryDto {
        return ApplicationResponsesHistoryDto(
            responses = applicationResponseRepository.findAllResponsesByApplicationId(application.id)
        )
    }

    fun getAllApplications(): List<Ticket> {
        return applicationRepository.findAll()
    }

    fun getImageByFilename(filename: String): String {
        return fileService.getPresignedUrl(filename, bucketName)
    }

    fun getResponses(id: Int, token: String): List<TicketComments> {
        return applicationRepository.findApplicationById(id)?.let { application ->
            userService.loadUserByEmail(jwtService.getUsername(jwtService.extractToken(token)))?.let { user ->
                if (application.userId != user.id) {
                    throw UserNotFoundException("User is not the author of this application")
                }
                return applicationResponseRepository.findAllResponsesByApplicationId(application.id)
            } ?: adminService.loadAdminByEmail(jwtService.getUsername(jwtService.extractToken(token)))?.let {
                return applicationResponseRepository.findAllResponsesByApplicationId(application.id)
            } ?: throw UserNotFoundException("User not found")
        } ?: throw NotFoundException("Application not found")
    }


    fun getAllApplicationsByUser(token: String): List<Ticket> {
        return userService.loadUserByEmail(jwtService.getUsername(jwtService.extractToken(token)))?.let { user ->
            return applicationRepository.findAllByUserId(user.id)
        } ?: throw UserNotFoundException("User not found")
    }

}