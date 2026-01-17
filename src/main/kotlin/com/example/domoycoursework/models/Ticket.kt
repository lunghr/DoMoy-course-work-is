package com.example.domoycoursework.models

import com.example.domoycoursework.models.enums.TicketStatus
import com.example.domoycoursework.models.enums.TicketTheme
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "tickets")
class Ticket(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @CreationTimestamp
    @Column(updatable = false)
    var createdAt: LocalDateTime? = null,

    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null,

    @Column(nullable = false)
    var status: TicketStatus = TicketStatus.NEW,

    var theme: TicketTheme,
    var title: String,
    var description: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var author: User,

    @ManyToOne(fetch = FetchType.LAZY)
    var assignedTo: User? = null,

    @OneToMany(mappedBy = "ticket", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var comments: MutableList<TicketComments> = mutableListOf()
)