package com.example.domoycoursework.repos

import com.example.domoycoursework.models.enums.Role
import com.example.domoycoursework.models.Admin
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.sql.ResultSet

@Service
class AdminRepository(
    private var jdbcTemplate: JdbcTemplate
) {
    fun save(admin: Admin): Admin =
        jdbcTemplate.query(
            "SELECT * FROM create_admin(?,?,?)",
            arrayOf(admin.phoneNumber, admin.email, admin.password)
        ) { rs, _ -> toAdmin(rs) }.first()

    fun delete(admin: Admin) {
        jdbcTemplate.query("SELECT * FROM delete_admin(?)", arrayOf(admin.id)) { rs, _ -> toAdmin(rs) }
    }

    fun findByEmail(email: String): Admin? =
        jdbcTemplate.query("SELECT * FROM find_admin_by_email(?)", arrayOf(email)) { rs, _ -> toAdmin(rs) }.firstOrNull()

    fun findByPhoneNumber(phoneNumber: String): Admin? =
        jdbcTemplate.query("SELECT * FROM find_admin_by_phone_number(?)", arrayOf(phoneNumber)) { rs, _ -> toAdmin(rs) }.firstOrNull()

    fun findAll(): List<Admin> =
        jdbcTemplate.query("SELECT * FROM find_all_admins()") { rs, _ -> toAdmin(rs) }

    fun setAdditionalAdminData(admin: Admin): Admin =
        jdbcTemplate.query(
            "SELECT * FROM set_additional_data_to_admin(?,?,?,?)",
            arrayOf(admin.id, admin.firstName, admin.lastName, admin.chatName)
        ) { rs, _ -> toAdmin(rs) }.first()

    fun toAdmin(rs: ResultSet): Admin = Admin(
        id = rs.getInt("id"),
        phoneNumber = rs.getString("phone_number"),
        email = rs.getString("email"),
        firstName = rs.getString("first_name"),
        lastName = rs.getString("last_name"),
        password = rs.getString("password"),
        role = Role.valueOf(rs.getString("role")),
        chatName = rs.getString("chat_name")
    )

}
