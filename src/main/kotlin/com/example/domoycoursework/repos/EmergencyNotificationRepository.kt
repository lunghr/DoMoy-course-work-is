package com.example.domoycoursework.repos

import com.example.domoycoursework.dto.EmergencyNotificationDto
import com.example.domoycoursework.models.EmergencyPost
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.sql.ResultSet
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class EmergencyNotificationRepository(
    private var jdbcTemplate: JdbcTemplate
) {
    fun save(emergencyNotificationDto: EmergencyNotificationDto, adminId: Int): EmergencyPost =
        jdbcTemplate.query(
            "SELECT * FROM create_emergency_notification(?, ?, ?, ?)",
            arrayOf(
                emergencyNotificationDto.title,
                emergencyNotificationDto.description,
                emergencyNotificationDto.houseId,
                adminId
            )
        ) { rs, _ ->
            toEmergencyNotification(rs)
        }.first()

    fun toEmergencyNotification(rs: ResultSet): EmergencyPost = EmergencyPost(
        id = rs.getInt("id"),
        title = rs.getString("title"),
        description = rs.getString("description"),
        date = LocalDateTime.parse(
            rs.getString("date").substring(0, 19).replace("T", " "),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        ).toString(),
        houseId = rs.getInt("house_id"),
        adminId = rs.getInt("admin_id")
    )
}