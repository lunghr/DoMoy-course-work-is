package com.example.domoycoursework.services

import com.example.domoycoursework.dto.PostDto
import com.example.domoycoursework.exceptions.FileException
import com.example.domoycoursework.exceptions.NoPermissionException
import com.example.domoycoursework.exceptions.NotFoundException
import com.example.domoycoursework.models.Admin
import com.example.domoycoursework.models.Post
import com.example.domoycoursework.repos.PostRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.cdimascio.dotenv.Dotenv
import io.minio.MinioClient
import io.minio.PutObjectArgs
import io.minio.RemoveObjectArgs
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
class PostService(
    private val postRepository: PostRepository,
    private val fileService: FileService,
    private val jwtService: JwtService,
    private val userService: UserService,
    private val adminService: AdminService
) {

    //TODO: change to System.getenv("MINIO_BUCKET")
    private final val dotenv: Dotenv = Dotenv.load()
    var bucketName: String = dotenv["MINIO_POST_BUCKET"]

    fun createPost(postDto: PostDto, token: String, image: MultipartFile?): Post {
        return postRepository.save(
            Post(
                id = 0L,
                title = postDto.title,
                content = postDto.content,
                fileName = image?.let { fileService.saveFile(it, bucketName) },
                author = jwtService.getUsername(jwtService.extractToken(token))
            )
        )
    }

    fun updatePost(id: Long, postDto: PostDto, token: String, image: MultipartFile?): Post {
        return postRepository.findPostById(id)?.let { post ->
            val redactor = userService.loadUserByEmail(jwtService.getUsername(jwtService.extractToken(token)))
                ?: adminService.loadAdminByEmail(jwtService.getUsername(jwtService.extractToken(token)))
            if (post.author != jwtService.getUsername(jwtService.extractToken(token)) && redactor !is Admin) throw NoPermissionException(
                "You are not the author of this post"
            )
            println(image?.isEmpty)
            post.title = postDto.title
            post.content = postDto.content
            val oldFileName = post.fileName
            post.fileName = image?.takeIf { !it.isEmpty }?.let {
                oldFileName?.let { old -> fileService.removeFile(old, bucketName) }
                fileService.saveFile(it, bucketName)
            } ?: run {
                oldFileName
            }
            println(post.fileName)
            postRepository.save(post)
        } ?: throw NotFoundException("Post not found")
    }

    fun deletePost(id: Long, token: String) {
        postRepository.findPostById(id)?.let { post ->
            val redactor = userService.loadUserByEmail(jwtService.getUsername(jwtService.extractToken(token)))
                ?: adminService.loadAdminByEmail(jwtService.getUsername(jwtService.extractToken(token)))
            if (post.author != jwtService.getUsername(jwtService.extractToken(token)) && redactor !is Admin) throw NoPermissionException(
                "You are not the author of this post"
            )
            post.fileName?.let { fileService.removeFile(it, bucketName) }
            postRepository.delete(post)
        } ?: throw NotFoundException("Post not found")
    }


    fun convertToDto(postDto: String): PostDto {
        return jacksonObjectMapper().readValue(postDto)
    }

}