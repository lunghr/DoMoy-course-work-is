package com.example.domoycoursework.models

import com.example.domoycoursework.enums.Role
import com.example.domoycoursework.enums.VerificationStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
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
    val id: Int,

    @Column(name = "phone_number", nullable = false)
    var phoneNumber: String,

    @Column(name = "email", nullable = false)
    var email: String,

    @Column(name = "first_name", nullable = true)
    var firstName: String? = null,

    @Column(name = "last_name", nullable = true)
    var lastName: String? = null,

    @Column(name = "password", nullable = false)
    private var password: String,

    var flatId: Int? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false) var role: Role = Role.ROLE_USER,

    @Column(name = "verification status", nullable = false)
    @Enumerated(EnumType.STRING)
    var verificationStatus: VerificationStatus = VerificationStatus.UNVERIFIED,

    @Column(name = "chat_name", nullable = true)
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
