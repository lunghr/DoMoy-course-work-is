package com.example.domoycoursework.models

import com.example.domoycoursework.models.enums.TicketStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime


@Entity
@Table(name = "ticket_comments")
class TicketComments(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @CreationTimestamp
    @Column(updatable = false)
    var createdAt: LocalDateTime? = null,

    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null,

    var newStatus: TicketStatus? = null,
    var text: String,

    @ManyToOne(fetch = FetchType.LAZY)
    var author: User,
)