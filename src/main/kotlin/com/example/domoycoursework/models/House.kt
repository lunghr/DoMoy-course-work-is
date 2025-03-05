package com.example.domoycoursework.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*



class House(
    val id: Int,
    var address: String,
)