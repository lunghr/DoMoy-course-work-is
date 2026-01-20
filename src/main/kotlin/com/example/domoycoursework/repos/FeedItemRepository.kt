package com.example.domoycoursework.repos

import com.example.domoycoursework.models.FeedItem
import org.springframework.data.jpa.repository.JpaRepository

interface FeedItemRepository : JpaRepository<FeedItem, Long> {
}