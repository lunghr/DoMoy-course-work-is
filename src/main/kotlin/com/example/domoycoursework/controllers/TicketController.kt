package com.example.domoycoursework.controllers

import com.example.domoycoursework.dto.TicketCommentDto
import com.example.domoycoursework.dto.TicketDto
import com.example.domoycoursework.models.Ticket
import com.example.domoycoursework.models.TicketComment
import com.example.domoycoursework.services.TicketService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ticket")
@CrossOrigin(origins = ["*"])
class TicketController(
    private var ticketService: TicketService
) {

    @PostMapping
    fun createTicket(
        @RequestHeader("Authorization") token: String,
        @RequestBody ticketDto: TicketDto
    ): Ticket {
        return ticketService.createTicket(ticketDto, token)
    }

    @PostMapping("/{id}/comment")
    fun createResponse(
        @RequestHeader("Authorization") token: String,
        @RequestBody responseDto: TicketCommentDto,
        @PathVariable("id") id: Long
    ): ResponseEntity<Any> {
        return ticketService.createResponse(responseDto, token, id)
    }

    @GetMapping("/{id}")
    fun getTicket(
        @PathVariable("id") id: Long
    ): Ticket {
        return ticketService.getTicketById(id)
    }

    @GetMapping("/all")
    fun getAllApplications(): List<Ticket> {
        return ticketService.getAllTickets()
    }

    @GetMapping("/{id}/comment")
    fun getResponses(
        @PathVariable("id") id: Long
    ): List<TicketComment> {
        return ticketService.getTicketCommentsByTicketId(id)
    }

    @GetMapping("/by-user/{id}")
    fun getAllApplicationsByUser(
        @PathVariable("id") id: Long
    ): List<Ticket> {
        return ticketService.getAllTicketsByUser(id)
    }
}