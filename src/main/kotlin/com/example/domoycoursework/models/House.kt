package com.example.domoycoursework.models

import jakarta.persistence.*


@Entity
@Table(name = "houses")
class House(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "address", nullable = false)
    var address: String,

    @OneToMany(mappedBy = "house")
    var flats: List<Flat> = mutableListOf()
)