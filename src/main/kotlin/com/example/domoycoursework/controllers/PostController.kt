package com.example.domoycoursework.controllers

import com.example.domoycoursework.dto.EmergencyPostDto
import com.example.domoycoursework.dto.NewsPostDto
import com.example.domoycoursework.models.EmergencyPost
import com.example.domoycoursework.models.FeedItem
import com.example.domoycoursework.models.NewsPost
import com.example.domoycoursework.services.PostService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/post")
@CrossOrigin(origins = ["*"])
class PostController(
    private var postService: PostService
) {

    @PostMapping("/news")
    fun createPost(
        @RequestHeader("Authorization") token: String,
        @RequestPart("postDto") @Valid dto: NewsPostDto,
        @RequestPart("image", required = false) image: MultipartFile?
    ): NewsPost {
        return postService.createNewsPost(dto, token, image)
    }

    @PostMapping("/emergency")
    fun createEmergencyPost(
        @RequestHeader("Authorization") token: String,
        @RequestPart("postDto") @Valid dto: EmergencyPostDto
    ): EmergencyPost {
        return postService.createEmergencyPost(dto, token)
    }

    @GetMapping("/news")
    fun getAllNewsPosts(): List<NewsPost> {
        return postService.getAllNewsPosts()
    }

    @GetMapping("/emergency")
    fun getAllEmergencyPosts(): List<EmergencyPost> {
        return postService.getAllEmergencyPosts()
    }

    @PatchMapping("/news/{id}")
    fun updatePost(
        @RequestHeader("Authorization") token: String,
        @PathVariable id: Long,
        @RequestPart("postDto") @Valid dto: NewsPostDto,
        @RequestPart("image", required = false) image: MultipartFile?
    ): NewsPost {
        return postService.updateNewsPost(id, dto, token, image)
    }

    @DeleteMapping("/news/{id}")
    fun deletePost(@RequestHeader("Authorization") token: String, @PathVariable id: Long) {
        postService.deletePost(id, token)
    }

    @DeleteMapping("/emergency/{id}")
    fun deleteEmergencyPost(
        @RequestHeader("Authorization") token: String, @PathVariable id
        : Long
    ) {
        postService.deleteEmergencyPost(id, token)
    }

    @GetMapping()
    fun getNewsFeed(): List<FeedItem> {
        return postService.getNewsFeed()
    }

    @GetMapping("/image/{filename}")
    fun getImage(@PathVariable filename: String): String {
        return postService.getImageByFilename(filename)
    }
}