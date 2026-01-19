package com.example.domoycoursework.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "flats")
class Flat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var flatNumber: Int,

    var floor: Int,

    @Column(unique = true)
    var cadastralNumber: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id", nullable = false)
    var house: House,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "flat_owners",
        joinColumns = [JoinColumn(name = "flat_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    var owners: MutableList<User> = mutableListOf()
)