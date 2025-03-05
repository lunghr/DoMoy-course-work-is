package com.example.domoycoursework.controllers

import com.example.domoycoursework.dto.PostDto
import com.example.domoycoursework.models.Post
import com.example.domoycoursework.services.PostService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = ["*"])
@Tag(name = "Posts")
class PostController(
    private var postService: PostService
) {

    @PostMapping("/create")
    fun createPost(@RequestHeader("Authorization") token: String, @RequestPart("postDto") @Valid postDto: String, @RequestPart("image", required = false) image: MultipartFile? ): Post {
        return postService.createPost(postService.convertToDto(postDto), token, image)
    }

    @PostMapping("/update/{id}")
    fun updatePost(@RequestHeader("Authorization") token: String, @PathVariable id: Int, @RequestPart @Valid postDto: String, @RequestPart("image", required = false) image: MultipartFile? ): Post {
        return postService.updatePost(id,postService.convertToDto(postDto), token, image)
    }

    @DeleteMapping("/delete/{id}")
    fun deletePost(@RequestHeader("Authorization") token: String, @PathVariable id: Int) {
        postService.deletePost(id, token)
    }
}