package com.example.domoycoursework.repos

import com.example.domoycoursework.models.TicketComment
import org.springframework.data.jpa.repository.JpaRepository

interface TicketCommentRepository : JpaRepository<TicketComment, Long> {
}