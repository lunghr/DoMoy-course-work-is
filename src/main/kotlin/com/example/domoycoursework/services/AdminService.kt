package com.example.domoycoursework.services

import com.example.domoycoursework.models.SecretKey
import com.example.domoycoursework.models.Admin
import com.example.domoycoursework.repos.AdminRepository
import com.example.domoycoursework.exceptions.EmailAlreadyInUseException
import com.example.domoycoursework.exceptions.PhoneNumberAlreadyInUseException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AdminService @Autowired constructor(
    private val adminRepository: AdminRepository,
    private val secretKeyService: SecretKeyService,
) {
    fun createAdmin(admin: Admin, secretKey: SecretKey): Admin {
        getAdmins().takeIf { it.isEmpty() }?.let { return adminRepository.save(admin) }

        adminRepository.findByEmail(admin.email)?.let { throw EmailAlreadyInUseException("This email address already in use") }
        adminRepository.findByPhoneNumber(admin.phoneNumber)
            ?.let { throw PhoneNumberAlreadyInUseException("This phone number already in use") }

        return adminRepository.save(admin).also { secretKeyService.useSecretKey(secretKey.key, it) }
    }

    fun getAdmins(): List<Admin> = adminRepository.findAll()

    fun loadAdminByPhoneNumber(phoneNumber: String): Admin? {
        return adminRepository.findByPhoneNumber(phoneNumber)
    }

    fun loadAdminByEmail(email: String): Admin? {
        return adminRepository.findByEmail(email)
    }

}
