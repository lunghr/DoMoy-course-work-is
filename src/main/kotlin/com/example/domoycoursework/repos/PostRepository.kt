package com.example.domoycoursework.repos

import com.example.domoycoursework.dto.PostDto
import com.example.domoycoursework.models.Post
import com.example.domoycoursework.services.FileService
import io.github.cdimascio.dotenv.Dotenv
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.sql.ResultSet
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class PostRepository(
    private var jdbcTemplate: JdbcTemplate,
    private var fileService: FileService
) {

    //TODO: change to System.getenv("MINIO_BUCKET")
    private final val dotenv: Dotenv = Dotenv.load()
    private var bucketName: String = "postimages"

    fun createPost(postDto: PostDto, author: String, file: MultipartFile?): Post? =
        jdbcTemplate.query(
            "SELECT * FROM create_post(?,?,?,?)", arrayOf(
                postDto.title,
                postDto.content,
                file?.let { fileService.saveFile(it, bucketName) },
                author
            )
        ) { rs, _ -> toPost(rs) }.firstOrNull()

    fun updatePost(id: Int, postDto: PostDto, author: String, file: MultipartFile?): Post? =
        jdbcTemplate.query(
            "SELECT * FROM update_post(?,?,?,?)", arrayOf(
                id,
                postDto.title,
                postDto.content,
                file?.let { fileService.saveFile(it, bucketName) },
            )
        ) { rs, _ -> toPost(rs) }.firstOrNull()

    fun delete(post: Post){
        jdbcTemplate.query(
            "SELECT * FROM delete_post(?)", arrayOf(post.id)
        ) { rs, _ -> toPost(rs) }.firstOrNull()
    }

    fun findPostById(id: Int): Post? =
        jdbcTemplate.query(
            "SELECT * FROM find_post_by_id(?)", arrayOf(id)
        ) { rs, _ -> toPost(rs) }.firstOrNull()

    fun findAll(): List<Post> = jdbcTemplate.query("SELECT * FROM find_all_posts()") { rs, _ ->
        toPost(rs)
    }


    fun toPost(rs: ResultSet): Post = Post(
        id = rs.getInt("id"),
        title = rs.getString("title"),
        content = rs.getString("content"),
        createdAt = LocalDateTime.parse(
            rs.getString("created_at").substring(0, 19).replace("T", " "),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        ).toString(),
        fileName = rs.getString("filename"),
        author = rs.getString("author")


    )
}