package com.example.domoycoursework.repos

import com.example.domoycoursework.models.NewsPost
import org.springframework.data.jpa.repository.JpaRepository

interface NewsPostRepository : JpaRepository<NewsPost, Long> {
}