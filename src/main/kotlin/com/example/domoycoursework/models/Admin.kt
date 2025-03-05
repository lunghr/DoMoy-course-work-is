package com.example.domoycoursework.models


import com.example.domoycoursework.enums.Role
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
    val id: Int,

    @Column(name = "phone_number", nullable = false)
    var phoneNumber: String,

    @Column(name = "email", nullable = false)
    var email: String,

    @Column(name = "first_name", nullable = true)
    var firstName: String? = "",

    @Column(name = "last_name", nullable = true)
    var lastName: String? = "",

    @Column(name = "password", nullable = false)
    private var password: String,

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    var role: Role = Role.ROLE_ADMIN,

    @Column(name = "chat_name", nullable = true)
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
