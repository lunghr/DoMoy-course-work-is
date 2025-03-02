package com.example.domoycoursework.models

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*


@Entity
@Table(name = "flats")
class Flat (
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne
    @JoinColumn(name = "house_id", nullable = false)
    var house: House,

    @Column(name = "number", nullable = false)
    var flatNumber: Int,

    @Column(name = "cadastral_number", nullable = false)
    var cadastralNumber: Long,

    @OneToOne
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonBackReference
    var owner: User
)