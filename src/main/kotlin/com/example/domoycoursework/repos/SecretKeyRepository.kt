package com.example.domoycoursework.repos

import com.example.domoycoursework.models.SecretKey
import org.springframework.stereotype.Service
import org.springframework.jdbc.core.JdbcTemplate

@Service
class SecretKeyRepository(
    private var jdbcTemplate: JdbcTemplate
) {
    fun save(key: String): String =
        jdbcTemplate.query("SELECT * FROM create_secret_key(?)", arrayOf(key)) { rs, _ -> toSecretKey(rs) }.first().key

    fun findSecretKeyByKey(key: String): SecretKey? =
        jdbcTemplate.query("SELECT * FROM find_secret_key_by_key(?)", arrayOf(key)) { rs, _ -> toSecretKey(rs) }.firstOrNull()

    fun useSecretKey(key: String, adminId: Int): SecretKey =
        jdbcTemplate.query("SELECT * FROM use_key(?,?)", arrayOf(key, adminId)) { rs, _ -> toSecretKey(rs) }.first()
    fun toSecretKey(rs: java.sql.ResultSet): SecretKey {
        return SecretKey(
            id = rs.getInt("id"),
            key = rs.getString("key"),
            isUsed = rs.getBoolean("is_used"),
            adminId = rs.getInt("admin_id")
        )
    }

}