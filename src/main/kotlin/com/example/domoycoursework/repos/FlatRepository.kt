package com.example.domoycoursework.repos

import com.example.domoycoursework.models.Flat
import com.example.domoycoursework.models.House
import org.springframework.data.jpa.repository.JpaRepository

interface FlatRepository : JpaRepository<Flat, Long> {
    fun findByHouseId(houseId: Long): List<Flat>
    fun existsByHouseAndFlatNumber(house: House, flatNumber: Int): Boolean
    fun findByOwnersId(ownerId: Long): List<Flat>
    fun findByCadastralNumber(cadastralNumber: String): Flat
}