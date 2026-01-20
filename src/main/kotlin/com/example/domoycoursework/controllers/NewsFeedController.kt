package com.example.domoycoursework.controllers

import com.example.domoycoursework.dto.EmergencyPostDto
import com.example.domoycoursework.dto.NewsPostDto
import com.example.domoycoursework.models.EmergencyPost
import com.example.domoycoursework.models.FeedItem
import com.example.domoycoursework.models.NewsPost
import com.example.domoycoursework.services.PostService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/newsfeed")
@CrossOrigin(origins = ["*"])
class NewsFeedController(private val postService: PostService) {

    @PostMapping("/news")
    fun createNewsPost(
        @RequestHeader("Authorization") token: String,
        @RequestPart("postDto") @Valid dto: NewsPostDto,
        @RequestPart("image", required = false) image: MultipartFile?
    ): ResponseEntity<NewsPost> {
        val post = postService.createNewsPost(dto, token, image)
        return ResponseEntity.status(HttpStatus.CREATED).body(post)
    }

    @PostMapping("/emergency")
    fun createEmergencyPost(
        @RequestHeader("Authorization") token: String,
        @RequestPart("postDto") @Valid dto: EmergencyPostDto
    ): ResponseEntity<EmergencyPost> {
        val post = postService.createEmergencyPost(dto, token)
        return ResponseEntity.status(HttpStatus.CREATED).body(post)
    }

    @GetMapping("/news")
    fun getAllNewsPosts(): ResponseEntity<List<NewsPost>> {
        return ResponseEntity.ok(postService.getAllNewsPosts())
    }

    @GetMapping("/emergency")
    fun getAllEmergencyPosts(): ResponseEntity<List<EmergencyPost>> {
        return ResponseEntity.ok(postService.getAllEmergencyPosts())
    }

    @GetMapping("/news/{id}")
    fun getNewsPostById(@PathVariable id: Long): ResponseEntity<NewsPost> {
        val post = postService.getNewsPostById(id)
        return ResponseEntity.ok(post)
    }

    @PatchMapping("/news/{id}")
    fun updateNewsPost(
        @RequestHeader("Authorization") token: String,
        @PathVariable id: Long,
        @RequestPart("postDto") @Valid dto: NewsPostDto,
        @RequestPart("image", required = false) image: MultipartFile?
    ): ResponseEntity<NewsPost> {
        val post = postService.updateNewsPost(id, dto, token, image)
        return ResponseEntity.ok(post)
    }

    @DeleteMapping("/news/{id}")
    fun deleteNewsPost(@RequestHeader("Authorization") token: String, @PathVariable id: Long): ResponseEntity<Void> {
        postService.deleteNewsPost(id, token)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/emergency/{id}")
    fun getEmergencyPostById(@PathVariable id: Long): ResponseEntity<EmergencyPost> {
        val post = postService.getEmergencyPostById(id)
        return ResponseEntity.ok(post)
    }

    @DeleteMapping("/emergency/{id}")
    fun deleteEmergencyPost(@RequestHeader("Authorization") token: String, @PathVariable id: Long): ResponseEntity<Void> {
        postService.deleteEmergencyPost(id, token)
        return ResponseEntity.noContent().build()
    }

    @GetMapping()
    fun getNewsFeed(): ResponseEntity<List<FeedItem>> {
        return ResponseEntity.ok(postService.getNewsFeed())
    }
}