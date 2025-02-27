package com.example.domoycoursework.users.user.models

import com.example.domoycoursework.auth.dto.Role
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
class User(
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "phone_number", nullable = false)
    var phoneNumber: String,

    @Column(name = "email", nullable = false)
    var email: String,

    @Column(name = "first_name", nullable = true)
    private var firstName: String? = null,

    @Column(name = "last_name", nullable = true)
    private var lastName: String? = null,

    @Column(name = "password", nullable = false)
    private var password: String,

    @Column(name = "EGRN", nullable = true)
    private var EGRN: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false) var role: Role = Role.ROLE_USER
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
