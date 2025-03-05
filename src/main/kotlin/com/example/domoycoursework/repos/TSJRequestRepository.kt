package com.example.domoycoursework.repos

import com.example.domoycoursework.enums.RequestStatus
import com.example.domoycoursework.models.TSJRequest
import com.example.domoycoursework.models.User
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class TSJRequestRepository(
    private var jdbcTemplate: JdbcTemplate
) {
    fun createTSJRequest(userId: Int): TSJRequest =
        jdbcTemplate.query("SELECT * FROM create_tsj_request(?)", arrayOf(userId)) { rs, _ ->
           toTsjRequest(rs)
        }.first()

    fun changeTSJRequestStatus(id: Int, status: RequestStatus): TSJRequest =
        jdbcTemplate.query("SELECT * FROM change_tsj_request_status(?, ?)", arrayOf(id, status.name)) { rs, _ ->
            toTsjRequest(rs)
        }.first()


    fun findTSJRequestById(id: Int): TSJRequest? =
        jdbcTemplate.query("SELECT * FROM find_tsj_request_by_id(?)", arrayOf(id)) { rs, _ ->
            toTsjRequest(rs)
        }.firstOrNull()


    fun findTSJRequestByUser(user: User): TSJRequest? =
        jdbcTemplate.query("SELECT * FROM find_tsj_requests_by_user_id(?)", arrayOf(user.id)) { rs, _ ->
            toTsjRequest(rs)
        }.firstOrNull()


    fun toTsjRequest(rs: java.sql.ResultSet): TSJRequest = TSJRequest(
        id = rs.getInt("id"),
        userId = rs.getInt("user_id"),
        status = RequestStatus.valueOf(rs.getString("status"))
    )


}

