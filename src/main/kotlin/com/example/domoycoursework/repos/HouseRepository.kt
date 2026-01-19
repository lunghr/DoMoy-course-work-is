package com.example.domoycoursework.repos
import com.example.domoycoursework.models.House
import org.springframework.data.jpa.repository.JpaRepository


interface HouseRepository : JpaRepository<House, Int> {
}