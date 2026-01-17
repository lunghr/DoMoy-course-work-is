package com.example.domoycoursework.models

import com.example.domoycoursework.models.enums.Role
import com.example.domoycoursework.models.enums.VerificationStatus
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class User(
    val id: Int,
    var phoneNumber: String,
    var email: String,
    var firstName: String? = null,
    var lastName: String? = null,
    private var password: String,
    var flatId: Int? = null,
    var role: Role = Role.ROLE_USER,
    var verificationStatus: VerificationStatus = VerificationStatus.UNVERIFIED,
    var chatName: String? = null
) : UserDetails {
    // Return list of roles
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role.name))
    }

    // Return password
    override fun getPassword(): String {
        return password
    }

    // Return username
    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true

}
