package com.example.domoycoursework.services

import com.example.domoycoursework.dto.TicketDto
import com.example.domoycoursework.dto.TicketCommentDto
import com.example.domoycoursework.models.enums.TicketStatus
import com.example.domoycoursework.exceptions.NoPermissionException
import com.example.domoycoursework.exceptions.NotFoundException
import com.example.domoycoursework.models.Ticket
import com.example.domoycoursework.models.TicketComment
import com.example.domoycoursework.models.enums.TicketTheme
import com.example.domoycoursework.repos.TicketRepository
import com.example.domoycoursework.repos.TicketCommentRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class TicketService(
    private var ticketRepository: TicketRepository,
    private var ticketCommentRepository: TicketCommentRepository,
    private var userService: UserService,
    private var jwtService: JwtService
) {
    fun createTicket(
        ticketDto: TicketDto,
        token: String
    ): Ticket {
        val ticket = Ticket(
            theme = TicketTheme.valueOf(ticketDto.theme),
            title = ticketDto.title,
            description = ticketDto.description,
            author = userService.findUser(jwtService.getUsername(jwtService.extractToken(token)))
        )
        return ticketRepository.save(ticket)
    }


    fun createResponse(
        ticketCommentDto: TicketCommentDto,
        token: String,
        ticketId: Long
    ): TicketComment {
        val ticket = ticketRepository.findById(ticketId).orElseThrow { NotFoundException("Ticket not found") }
        val admin = userService.findUser(jwtService.getUsername(jwtService.extractToken(token)))

        if (ticket.status == TicketStatus.COMPLETED || ticket.status == TicketStatus.REJECTED) {
            throw NoPermissionException("Ticket is already completed or rejected")
        }

        if (admin.role.name != "ADMIN") {
            throw NoPermissionException("Only admins can respond to tickets")
        }

        ticket.status = TicketStatus.valueOf(ticketCommentDto.newStatus)
        ticket.assignedTo = admin
        ticketRepository.save(ticket)


        return ticketCommentRepository.save(
            TicketComment(
                text = ticketCommentDto.text,
                ticket = ticket,
                author = admin,
                newStatus = TicketStatus.valueOf(ticketCommentDto.newStatus)
            )
        )
    }

    fun getTicketById(id: Long): Ticket {
        return ticketRepository.findById(id).orElseThrow { NotFoundException("Ticket not found") }
    }

    fun getTicketCommentsByTicketId(id: Long): List<TicketComment> {
        return ticketCommentRepository.findAllByTicketId(id)
    }


    fun getAllTickets(): List<Ticket> {
        return ticketRepository.findAll()
    }

    fun getAllTicketsByUser(id: Long): List<Ticket> {
        return ticketRepository.findAllByAuthorId(id)
    }

}