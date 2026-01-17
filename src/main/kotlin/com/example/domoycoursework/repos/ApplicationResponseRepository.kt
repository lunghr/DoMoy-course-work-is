package com.example.domoycoursework.repos

import com.example.domoycoursework.dto.ApplicationResponseDto
import com.example.domoycoursework.models.enums.ApplicationStatus
import com.example.domoycoursework.models.ApplicationResponse
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.sql.ResultSet
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class ApplicationResponseRepository(
    private var jdbcTemplate: JdbcTemplate
) {

    fun createApplicationResponse(
        applicationResponseDto: ApplicationResponseDto,
        adminId: Int,
        applicationId: Int
    ): ApplicationResponse =
        jdbcTemplate.queryForObject(
            "SELECT * FROM create_application_response(?, ?, ?, ?)", arrayOf(
                applicationId,
                adminId,
                applicationResponseDto.response,
                applicationResponseDto.status
            )
        ) { rs, _ ->
            toApplicationResponse(rs)
        } ?: throw Exception("Failed to create application response")

    fun findAllResponsesByApplicationId(applicationId: Int): List<ApplicationResponse> =
        jdbcTemplate.query(
            "SELECT * FROM find_all_responses_by_application_id(?)", arrayOf(applicationId)
        ) { rs, _ ->
            toApplicationResponse(rs)
        }

    fun toApplicationResponse(rs: ResultSet): ApplicationResponse = ApplicationResponse(
        id = rs.getInt("id"),
        applicationId = rs.getInt("application_id"),
        adminId = rs.getInt("admin_id"),
        response = rs.getString("response"),
        status = ApplicationStatus.valueOf(rs.getString("status")),
        date = LocalDateTime.parse(
            rs.getString("date").substring(0, 19).replace("T", " "),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        ).toString(),
    )
}