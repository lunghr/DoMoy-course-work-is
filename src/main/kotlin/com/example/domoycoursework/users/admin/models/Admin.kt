package com.example.domoycoursework.users.admin.models


import com.example.domoycoursework.auth.dto.Role
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "admins")
class Admin(
    @Id
    @Column(name = "admin_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long,

    @Column(name = "phone_number", nullable = false)
    var phoneNumber: String,

    @Column(name = "email", nullable = false)
    var email: String,

    @Column(name = "first_name", nullable = true)
    private var firstName: String? = "",

    @Column(name = "last_name", nullable = true)
    private var lastName: String? = "",

    @Column(name = "password", nullable = false)
    private var password: String,

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private var role: Role = Role.ROLE_ADMIN

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
