package com.example.domoycoursework.users.admin.services

import com.example.domoycoursework.users.admin.secretKeys.models.SecretKey
import com.example.domoycoursework.users.admin.secretKeys.services.SecretKeyService
import com.example.domoycoursework.users.admin.models.Admin
import com.example.domoycoursework.users.admin.repos.AdminRepository
import com.example.domoycoursework.exceptions.models.EmailAlreadyInUse
import com.example.domoycoursework.exceptions.models.PhoneNumberAlreadyInUse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AdminService @Autowired constructor(
    private val adminRepository: AdminRepository,
    private val secretKeyService: SecretKeyService,
) {
    fun createAdmin(admin: Admin, secretKey: SecretKey): Admin {
        getAdmins().takeIf { it.isEmpty() }?.let { return adminRepository.save(admin) }

        adminRepository.findByEmail(admin.email)?.let { throw EmailAlreadyInUse("This email address already in use") }
        adminRepository.findByPhoneNumber(admin.phoneNumber)
            ?.let { throw PhoneNumberAlreadyInUse("This phone number already in use") }

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
