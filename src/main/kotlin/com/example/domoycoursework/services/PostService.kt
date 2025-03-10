package com.example.domoycoursework.services

import com.example.domoycoursework.dto.PostDto
import com.example.domoycoursework.enums.Role
import com.example.domoycoursework.exceptions.FileException
import com.example.domoycoursework.exceptions.InvalidUserDataException
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
    var bucketName: String = "postimages"


    fun createPost(postDto: PostDto, token: String, image: MultipartFile?): Post {
        println("PostService.bucketName: $bucketName")
        return postRepository.createPost(postDto, jwtService.getUsername(jwtService.extractToken(token)), image)
            ?: throw InvalidUserDataException("Post not created")

    }

    fun updatePost(id: Int, postDto: PostDto, token: String, image: MultipartFile?): Post {
        return postRepository.findPostById(id)?.let { post ->
            val redactor = userService.loadUserByEmail(jwtService.getUsername(jwtService.extractToken(token)))
                ?: adminService.loadAdminByEmail(jwtService.getUsername(jwtService.extractToken(token)))
            if (post.author != jwtService.getUsername(jwtService.extractToken(token)) && redactor !is Admin) throw NoPermissionException(
                "You are not the author of this post"
            )
            image?.takeIf { !it.isEmpty }?.let {
                post.fileName?.let { old -> fileService.removeFile(old, bucketName) }
            }
            postRepository.updatePost(id, postDto, jwtService.getUsername(jwtService.extractToken(token)), image)
                ?: throw InvalidUserDataException("Post not updated")
        } ?: throw NotFoundException("Post not found")
    }

    fun deletePost(id: Int, token: String) {
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


    fun getAllPosts(): List<Post> {
        return postRepository.findAll()
    }

    fun getImageByFilename(filename: String): String {
        return fileService.getPresignedUrl(filename, bucketName)
    }

    fun convertToDto(postDto: String): PostDto {
        return jacksonObjectMapper().readValue(postDto)
    }

}