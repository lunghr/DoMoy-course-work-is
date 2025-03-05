package com.example.domoycoursework.models


import com.example.domoycoursework.enums.Role
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class Admin(
    val id: Int,
    var phoneNumber: String,
    var email: String,
    var firstName: String? = "",
    var lastName: String? = "",
    private var password: String,
    var role: Role = Role.ROLE_ADMIN,
    var chatName: String? = null
    ) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role.name))
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true

}
