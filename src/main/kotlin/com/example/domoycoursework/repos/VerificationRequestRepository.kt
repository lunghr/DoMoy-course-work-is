package com.example.domoycoursework.repos

import com.example.domoycoursework.dto.VerificationRequestDto
import com.example.domoycoursework.enums.RequestStatus
import com.example.domoycoursework.exceptions.InvalidUserDataException
import com.example.domoycoursework.models.User
import com.example.domoycoursework.models.VerificationRequest
import com.example.domoycoursework.repos.VerificationRequestRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class VerificationRequestRepository(
    private var jdbcTemplate: JdbcTemplate
) {
    fun createVerificationRequest(verificationRequestDto: VerificationRequestDto, user: User): VerificationRequest {
        return jdbcTemplate.query(
            "SELECT * FROM create_verification_request(?,?,?,?,?,?)", arrayOf(
                verificationRequestDto.firstName,
                verificationRequestDto.lastName,
                verificationRequestDto.cadastralNumber,
                verificationRequestDto.address,
                verificationRequestDto.flatNumber,
                user.id
            )
        ) { rs, _ ->
            toVerificationRequest(rs)
        }.firstOrNull() ?: throw InvalidUserDataException("Verification request not created")
    }

    fun save(verificationRequestDto: VerificationRequestDto, id: Int): VerificationRequest {
        return jdbcTemplate.query(
            "SELECT * FROM save_verification_request(?,?,?,?,?,?)", arrayOf(
                id,
                verificationRequestDto.firstName,
                verificationRequestDto.lastName,
                verificationRequestDto.cadastralNumber,
                verificationRequestDto.address,
                verificationRequestDto.flatNumber,
            )
        ) { rs, _ ->
            toVerificationRequest(rs)
        }.firstOrNull() ?: throw InvalidUserDataException("Verification request not saved")
    }

    fun changeVerificationRequestStatus(id: Int, status: RequestStatus): VerificationRequest {
        return jdbcTemplate.query(
            "SELECT * FROM change_verification_request_status(?,?)", arrayOf(
                id,
                status.toString()
            )
        ) { rs, _ ->
            toVerificationRequest(rs)
        }.firstOrNull() ?: throw InvalidUserDataException("Verification request status not changed")
    }

    fun findVerificationRequestById(id: Int): VerificationRequest? =
        jdbcTemplate.query("SELECT * FROM find_verification_request_by_id(?)", arrayOf(id)) { rs, _ ->
            toVerificationRequest(rs)
        }.firstOrNull()

    fun findVerificationRequestByUser(user: User): VerificationRequest? =
        jdbcTemplate.query("SELECT * FROM find_verification_requests_by_user_id(?)", arrayOf(user.id)) { rs, _ ->
            toVerificationRequest(rs)
        }.firstOrNull()


    fun findAll(): List<VerificationRequest> =
        jdbcTemplate.query("SELECT * FROM get_all_verification_requests()") { rs, _ ->
            toVerificationRequest(rs)
        }

    fun toVerificationRequest(rs: java.sql.ResultSet): VerificationRequest {
        return VerificationRequest(
            id = rs.getInt("id"),
            firstName = rs.getString("first_name"),
            lastName = rs.getString("last_name"),
            cadastralNumber = rs.getString("cadastral_number"),
            address = rs.getString("address"),
            flatNumber = rs.getInt("flat_number"),
            userId = rs.getInt("user_id"),
            status = RequestStatus.valueOf(rs.getString("status"))
        )
    }

}