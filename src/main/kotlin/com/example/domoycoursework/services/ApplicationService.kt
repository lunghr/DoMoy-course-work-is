package com.example.domoycoursework.services

import com.example.domoycoursework.dto.ApplicationRequestDto
import com.example.domoycoursework.dto.ApplicationResponseDto
import com.example.domoycoursework.dto.ApplicationResponsesHistoryDto
import com.example.domoycoursework.enums.ApplicationStatus
import com.example.domoycoursework.enums.ApplicationTheme
import com.example.domoycoursework.exceptions.NoPermissionException
import com.example.domoycoursework.exceptions.NotFoundException
import com.example.domoycoursework.exceptions.UserNotFoundException
import com.example.domoycoursework.models.Application
import com.example.domoycoursework.models.ApplicationResponse
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
    private final val dotenv: Dotenv = Dotenv.load()
    var bucketName: String = dotenv["MINIO_POST_BUCKET"]

    fun createApplication(
        applicationRequestDto: ApplicationRequestDto,
        token: String,
        files: List<MultipartFile>?
    ): Application {
        return userService.loadUserByEmail(jwtService.getUsername(jwtService.extractToken(token)))?.let {
            applicationRepository.save(
                Application(
                    id = 0,
                    user = it,
                    theme = ApplicationTheme.valueOf(applicationRequestDto.theme),
                    title = applicationRequestDto.title,
                    description = applicationRequestDto.description,
                    filenames = files?.map { file -> fileService.saveFile(file, bucketName) } ?: emptyList()
                ))
        } ?: throw UserNotFoundException("User not found")
    }

    fun createResponse(
        applicationResponseDto: ApplicationResponseDto,
        token: String,
        id: Long
    ): ApplicationResponsesHistoryDto {
        return adminService.loadAdminByEmail(jwtService.getUsername(jwtService.extractToken(token)))?.let { admin ->
            applicationRepository.findApplicationById(id)?.let { application ->
                if (application.status == ApplicationStatus.COMPLETED || application.status == ApplicationStatus.REJECTED) {
                    throw NoPermissionException("Application is already completed or rejected")
                }
                application.status = ApplicationStatus.valueOf(applicationResponseDto.status)
                applicationRepository.save(application)
                applicationResponseRepository.save(
                    ApplicationResponse(
                        id = 0,
                        application = application,
                        admin = admin,
                        response = applicationResponseDto.response,
                        status = ApplicationStatus.valueOf(applicationResponseDto.status)
                    )
                )
                return createHistory(application)
            } ?: throw NotFoundException("Application not found")
        } ?: throw NotFoundException("Admin not found")
    }

    fun getApplication(id: Long, token: String): Application {
        return applicationRepository.findApplicationById(id)?.let { application ->
            userService.loadUserByEmail(jwtService.getUsername(jwtService.extractToken(token)))?.let { user ->
                if (application.user != user) {
                    throw UserNotFoundException("User is not the author of this application")
                }
//                return createHistory(application)
                return application
            } ?: adminService.loadAdminByEmail(jwtService.getUsername(jwtService.extractToken(token)))?.let { admin ->
//                return createHistory(application)
                return application
            } ?: throw UserNotFoundException("User not found")
        } ?: throw NotFoundException("Application not found")
    }

    fun convertRequestToDto(applicationDto: String): ApplicationRequestDto {
        return jacksonObjectMapper().readValue(applicationDto)
    }

    fun createHistory(application: Application): ApplicationResponsesHistoryDto {
        return ApplicationResponsesHistoryDto(
            responses = application.responses.associate { response ->
                response.id to response.status
            },
        )
    }

}