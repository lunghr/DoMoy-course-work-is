package com.example.domoycoursework.services

import com.example.domoycoursework.dto.EmergencyPostDto
import com.example.domoycoursework.dto.NewsPostDto
import com.example.domoycoursework.exceptions.NoPermissionException
import com.example.domoycoursework.exceptions.NotFoundException
import com.example.domoycoursework.models.EmergencyPost
import com.example.domoycoursework.models.FeedItem
import com.example.domoycoursework.models.NewsPost
import com.example.domoycoursework.models.enums.SeverityLevel
import com.example.domoycoursework.repos.EmergencyPostRepository
import com.example.domoycoursework.repos.FeedItemRepository
import com.example.domoycoursework.repos.NewsPostRepository
import io.github.cdimascio.dotenv.Dotenv
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class PostService(
    private val newsPostRepository: NewsPostRepository,
    private val emergencyPostRepository: EmergencyPostRepository,
    private val feedItemRepository: FeedItemRepository,
    private val fileService: FileService,
    private val jwtService: JwtService,
    private val userService: UserService,
) {
    private final val dotenv: Dotenv = Dotenv.load()
    var bucketName: String = "postimages"


    @Transactional
    fun createNewsPost(
        dto: NewsPostDto,
        token: String,
        image: MultipartFile? = null
    ): NewsPost {
        val username = jwtService.getUsername(jwtService.extractToken(token))
        val author = userService.findUser(username)

        val imageUrl = image?.let {
            fileService.saveFile(it, bucketName)
        }

        val post = NewsPost(
            title = dto.title.trim(),
            content = dto.content.trim(),
            author = author,
            attachment = imageUrl
        )
        return newsPostRepository.save(post)
    }


    @Transactional
    fun createEmergencyPost(
        dto: EmergencyPostDto,
        token: String,
    ): EmergencyPost {
        val username = jwtService.getUsername(jwtService.extractToken(token))
        val author = userService.findUser(username)

        if (author.role.name != "ADMIN") {
            throw NoPermissionException("Only admins can create emergency posts")
        }

        val post = EmergencyPost(
            title = dto.title.trim(),
            content = dto.content.trim(),
            author = author,
            severityLevel = SeverityLevel.valueOf(dto.severityLevel)
        )
        return emergencyPostRepository.save(post)
    }

    fun updateNewsPost(id: Long, newsPostDto: NewsPostDto, token: String, image: MultipartFile?): NewsPost {
        val username = jwtService.getUsername(jwtService.extractToken(token))
        val redactor = userService.findUser(username)

        val post = newsPostRepository.findById(id).orElseThrow { NotFoundException("Post not found") }
        if (post.author.email != username || redactor.role.name != "ADMIN") throw NoPermissionException("You are not the author of this post")

        image?.takeIf { !it.isEmpty }?.let {
            post.attachment?.let { old -> fileService.removeFile(old, bucketName) }
            post.attachment = fileService.saveFile(it, bucketName)
        }

        post.title = newsPostDto.title.trim()
        post.content = newsPostDto.content.trim()
        return newsPostRepository.save(post)
    }


    fun deletePost(id: Long, token: String) {
        val username = jwtService.getUsername(jwtService.extractToken(token))
        val redactor = userService.findUser(username)

        newsPostRepository.findById(id).orElseThrow { NotFoundException("Post not found") }.let { post ->
            if (post.author.email != username || redactor.role.name != "ADMIN") throw NoPermissionException("You are not the author of this post")
            post.attachment?.let { fileService.removeFile(it, bucketName) }
            newsPostRepository.delete(post)
        }
    }

    fun deleteEmergencyPost(id: Long, token: String) {
        val username = jwtService.getUsername(jwtService.extractToken(token))
        val redactor = userService.findUser(username)

        emergencyPostRepository.findById(id).orElseThrow { NotFoundException("Post not found") }.let { post ->
            if (post.author.email != username && redactor.role.name != "ADMIN") throw NoPermissionException("You are not the author of this post")
            emergencyPostRepository.delete(post)
        }
    }

    fun getAllNewsPosts(): List<NewsPost> {
        return newsPostRepository.findAll()
    }

    fun getAllEmergencyPosts(): List<EmergencyPost> {
        return emergencyPostRepository.findAll()
    }


    fun getNewsFeed(): List<FeedItem> {
        return feedItemRepository.findAll();
    }

    fun getImageByFilename(filename: String): String {
        return fileService.getPresignedUrl(filename, bucketName)
    }
}