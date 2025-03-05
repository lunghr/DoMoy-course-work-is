package com.example.domoycoursework.services

import com.example.domoycoursework.dto.AdminAdditionalDataDto
import com.example.domoycoursework.models.SecretKey
import com.example.domoycoursework.models.Admin
import com.example.domoycoursework.repos.AdminRepository
import com.example.domoycoursework.exceptions.EmailAlreadyInUseException
import com.example.domoycoursework.exceptions.PhoneNumberAlreadyInUseException
import com.example.domoycoursework.exceptions.UserNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminService @Autowired constructor(
    private val adminRepository: AdminRepository,
    private val secretKeyService: SecretKeyService,
    private val jwtService: JwtService
) {
    @Transactional
    fun createAdmin(admin: Admin, secretKey: SecretKey): Admin {
        getAdmins().takeIf { it.isEmpty() }?.let { return adminRepository.save(admin) }

        adminRepository.findByEmail(admin.email)
            ?.let { throw EmailAlreadyInUseException("This email address already in use") }
        adminRepository.findByPhoneNumber(admin.phoneNumber)
            ?.let { throw PhoneNumberAlreadyInUseException("This phone number already in use") }
        val savedAdmin = adminRepository.save(admin)
        try {
            secretKeyService.useSecretKey(secretKey.key, savedAdmin)
            return savedAdmin
        } catch (e: Exception) {
            adminRepository.delete(savedAdmin)
            throw e
        }
    }

    fun setAdditionalAdminData(adminAdditionalDataDto: AdminAdditionalDataDto, token: String): Admin {
        return loadAdminByEmail(jwtService.getUsername(jwtService.extractToken(token)))?.let { admin ->
            admin.firstName = adminAdditionalDataDto.firstName
            admin.lastName = adminAdditionalDataDto.lastName
            admin.chatName = "${admin.firstName} ${admin.lastName}, admin"
            adminRepository.setAdditionalAdminData(admin)
        } ?: throw UserNotFoundException("Admin not found")
    }

    fun getAdmins(): List<Admin> = adminRepository.findAll()

    fun loadAdminByPhoneNumber(phoneNumber: String): Admin? {
        return adminRepository.findByPhoneNumber(phoneNumber)
    }

    fun loadAdminByEmail(email: String): Admin? {
        return adminRepository.findByEmail(email)
    }

}
