package com.example.domoycoursework.repos

import com.example.domoycoursework.models.Ticket
import org.springframework.data.jpa.repository.JpaRepository

interface TicketRepository : JpaRepository<Ticket, Long>{
    fun findAllByAuthorId(authorId: Long): MutableList<Ticket>
}