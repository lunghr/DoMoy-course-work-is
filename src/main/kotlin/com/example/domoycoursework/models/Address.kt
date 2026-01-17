package com.example.domoycoursework.models

import jakarta.persistence.Embeddable

@Embeddable
class Address(
    var city: String,
    var street: String,
    var houseNumber: String,
    var zipCode: String? = null
)