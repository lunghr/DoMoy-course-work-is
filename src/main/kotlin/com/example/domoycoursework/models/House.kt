package com.example.domoycoursework.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*


@Entity
@Table(name = "houses")
@JsonIgnoreProperties
class House(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "address", nullable = false)
    var address: String,

    @OneToMany(mappedBy = "house")
    @JsonManagedReference
    var flats: List<Flat> = mutableListOf()
)