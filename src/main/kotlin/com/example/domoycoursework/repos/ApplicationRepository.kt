package com.example.domoycoursework.repos

import com.example.domoycoursework.dto.ApplicationRequestDto
import com.example.domoycoursework.models.enums.TicketStatus
import com.example.domoycoursework.models.enums.TicketTheme
import com.example.domoycoursework.models.Ticket
import com.example.domoycoursework.services.FileService
import io.github.cdimascio.dotenv.Dotenv
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.sql.ResultSet
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class ApplicationRepository(
    private var jdbcTemplate: JdbcTemplate,
    private var fileService: FileService
) {
    fun createApplication(
        applicationRequestDto: ApplicationRequestDto,
        userId: Int,
        files: List<MultipartFile>?
    ): Ticket {
        //TODO: change to System.getenv("MINIO_BUCKET")
        val dotenv: Dotenv = Dotenv.load()
        val bucketName: String = "applicationimages"


        val application = jdbcTemplate.query(
            "SELECT * FROM create_application(?, ?, ?, ?)", arrayOf(
                userId,
                applicationRequestDto.theme,
                applicationRequestDto.title,
                applicationRequestDto.description
            )
        ) { rs, _ ->
            toApplication(rs)
        }.first()

        files?.map { file ->
            jdbcTemplate.query(
                "SELECT * FROM add_file_to_application(?,?)",
                arrayOf(application.id, fileService.saveFile(file, bucketName))
            ) { rs, _ ->
                rs.getString("file_name")
            }.first()
        } ?: emptyList()

        return application
    }

    fun updateApplicationStatus(id: Int, status: TicketStatus): Ticket? =
        jdbcTemplate.query(
            "SELECT * FROM change_application_status(?, ?)", arrayOf(id, status.toString())
        ) { rs, _ ->
            toApplication(rs)
        }.firstOrNull()


    fun findApplicationById(id: Int): Ticket? =
        jdbcTemplate.query(
            "SELECT * FROM find_application_by_id(?)", arrayOf(id)
        ) { rs, _ ->
            toApplication(rs)
        }.firstOrNull()


    fun findAll(): List<Ticket> = jdbcTemplate.query("SELECT * FROM find_all_applications()") { rs, _ ->
        toApplication(rs)
    }

    fun findAllByUserId(userId: Int): List<Ticket> = jdbcTemplate.query("SELECT * FROM find_all_applications_by_user_id(?)", arrayOf(userId)) { rs, _ ->
        toApplication(rs)
    }

    fun toApplication(rs: ResultSet): Ticket = Ticket(
        id = rs.getInt("id"),
        userId = rs.getInt("user_id"),
        theme = TicketTheme.valueOf(rs.getString("theme")),
        title = rs.getString("title"),
        description = rs.getString("description"),
        status = TicketStatus.valueOf(rs.getString("status")),
        createdAt = LocalDateTime.parse(
            rs.getString("created_at").substring(0, 19).replace("T", " "),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        ).toString()
    )
}