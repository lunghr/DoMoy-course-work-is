package com.example.domoycoursework.controllers

import com.example.domoycoursework.dto.TicketCommentDto
import com.example.domoycoursework.dto.TicketDto
import com.example.domoycoursework.models.Ticket
import com.example.domoycoursework.models.TicketComment
import com.example.domoycoursework.services.TicketService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tickets")
@CrossOrigin(origins = ["*"])
class TicketsController(
    private val ticketService: TicketService
) {

    @PostMapping
    fun createRequest(
        @RequestHeader("Authorization") token: String,
        @RequestBody @Valid ticketDto: TicketDto
    ): ResponseEntity<Ticket> {
        val ticket = ticketService.createTicket(ticketDto, token)
        return ResponseEntity.status(HttpStatus.CREATED).body(ticket)
    }

    @PostMapping("/{id}/comments")
    fun addComment(
        @RequestHeader("Authorization") token: String,
        @PathVariable id: Long,
        @RequestBody @Valid responseDto: TicketCommentDto
    ): ResponseEntity<TicketComment> {
        val comment = ticketService.createResponse(responseDto, token, id)
        return ResponseEntity.status(HttpStatus.CREATED).body(comment)
    }

    @GetMapping("/{id}")
    fun getRequest(@PathVariable id: Long): ResponseEntity<Ticket> {
        return ResponseEntity.ok(ticketService.getTicketById(id))
    }

    @GetMapping
    fun getAllRequests(): ResponseEntity<List<Ticket>> {
        return ResponseEntity.ok(ticketService.getAllTickets())
    }

    @GetMapping("/{id}/comments")
    fun getComments(@PathVariable id: Long): ResponseEntity<List<TicketComment>> {
        return ResponseEntity.ok(ticketService.getTicketCommentsByTicketId(id))
    }

    @GetMapping("/users/{userId}")
    fun getRequestsByUser(@PathVariable userId: Long): ResponseEntity<List<Ticket>> {
        return ResponseEntity.ok(ticketService.getAllTicketsByUser(userId))
    }
}