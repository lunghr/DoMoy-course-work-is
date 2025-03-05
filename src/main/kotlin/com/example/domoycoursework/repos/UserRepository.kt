package com.example.domoycoursework.repos

import com.example.domoycoursework.enums.Role
import com.example.domoycoursework.enums.VerificationStatus
import com.example.domoycoursework.models.Flat
import com.example.domoycoursework.models.User
import com.example.domoycoursework.models.VerificationRequest

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.sql.ResultSet

@Service
class UserRepository(
    private var jdbcTemplate: JdbcTemplate
) {
    fun save(user: User): User =
        jdbcTemplate.query(
            "SELECT * FROM create_user(?,?,?)",
            arrayOf(user.phoneNumber, user.email, user.password)
        ) { rs, _ -> toUser(rs) }.first()


    fun findUserByEmail(email: String): User? =
        jdbcTemplate.query("SELECT * FROM find_user_by_email(?)", arrayOf(email)) { rs, _ -> toUser(rs) }.firstOrNull()

    fun findUserByPhoneNumber(phoneNumber: String): User? =
        jdbcTemplate.query("SELECT * FROM find_user_by_phone_number(?)", arrayOf(phoneNumber)) { rs, _ -> toUser(rs) }
            .firstOrNull()

    fun findUserById(id: Int): User? =
        jdbcTemplate.query("SELECT * FROM find_user_by_id(?)", arrayOf(id)) { rs, _ -> toUser(rs) }.firstOrNull()

    fun changeRole(id: Int, role: Role): User =
        jdbcTemplate.query("SELECT * FROM change_user_role(?,?)", arrayOf(id, role.name)) { rs, _ -> toUser(rs) }
            .first()

    fun setAdditionalUserData(user: User, verificationRequest: VerificationRequest, flat: Flat, chatName: String): User =
        jdbcTemplate.query(
            "SELECT * FROM set_additional_data_to_user(?,?,?,?,?,?)",
            arrayOf(
                user.id,
                verificationRequest.firstName,
                verificationRequest.lastName,
                flat.id,
                VerificationStatus.VERIFIED.toString(),
                chatName
            )
        ) { rs, _ -> toUser(rs) }.first()

    fun toUser(rs: ResultSet): User = User(
        id = rs.getInt("id"),
        phoneNumber = rs.getString("phone_number"),
        email = rs.getString("email"),
        firstName = rs.getString("first_name"),
        lastName = rs.getString("last_name"),
        password = rs.getString("password"),
        flatId = rs.getInt("flat_id"),
        role = Role.valueOf(rs.getString("role")),
        verificationStatus = VerificationStatus.valueOf(rs.getString("verification_status")),
        chatName = rs.getString("chat_name")
    )

}
