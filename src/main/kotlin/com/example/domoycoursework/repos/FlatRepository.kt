package com.example.domoycoursework.repos

import com.example.domoycoursework.models.Flat
import com.example.domoycoursework.models.House
import org.springframework.data.jpa.repository.JpaRepository

interface FlatRepository: JpaRepository<Flat, Long>{
    fun findFlatByHouseAndFlatNumber(house: House, number: Int): Flat?
}