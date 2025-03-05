package com.example.domoycoursework.repos

import com.example.domoycoursework.dto.ApplicationRequestDto
import com.example.domoycoursework.enums.ApplicationStatus
import com.example.domoycoursework.enums.ApplicationTheme
import com.example.domoycoursework.models.Application
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
    ): Application {
        //TODO: change to System.getenv("MINIO_BUCKET")
        val dotenv: Dotenv = Dotenv.load()
        val bucketName: String = dotenv["MINIO_APPLICATION_IMAGES_BUCKET"]


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

    fun updateApplicationStatus(id: Int, status: ApplicationStatus): Application? =
        jdbcTemplate.query(
            "SELECT * FROM change_application_status(?, ?)", arrayOf(id, status.toString())
        ) { rs, _ ->
            toApplication(rs)
        }.firstOrNull()


    fun findApplicationById(id: Int): Application? =
        jdbcTemplate.query(
            "SELECT * FROM find_application_by_id(?)", arrayOf(id)
        ) { rs, _ ->
            toApplication(rs)
        }.firstOrNull()

    fun toApplication(rs: ResultSet): Application = Application(
        id = rs.getInt("id"),
        userId = rs.getInt("user_id"),
        theme = ApplicationTheme.valueOf(rs.getString("theme")),
        title = rs.getString("title"),
        description = rs.getString("description"),
        status = ApplicationStatus.valueOf(rs.getString("status")),
        createdAt = LocalDateTime.parse(
            rs.getString("created_at").substring(0, 19).replace("T", " "),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        ).toString()
    )
}