package com.example.domoycoursework.repos

import com.example.domoycoursework.models.Post

import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long> {
    fun findPostById(id: Long): Post?
}