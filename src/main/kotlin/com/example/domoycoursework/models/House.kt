package com.example.domoycoursework.models

import jakarta.persistence.CascadeType
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table


@Entity
@Table(name = "houses")
class House(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Embedded
    var address: Address,

    var totalFloors: Int,

    @OneToMany(mappedBy = "house", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var flats: MutableList<Flat> = mutableListOf()
)