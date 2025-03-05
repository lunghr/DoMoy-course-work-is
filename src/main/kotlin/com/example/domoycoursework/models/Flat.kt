package com.example.domoycoursework.models

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*


class Flat (
    val id: Int,
    var houseId: Int,
    var flatNumber: Int,
    var cadastralNumber: String,
    var ownerId: Int
)