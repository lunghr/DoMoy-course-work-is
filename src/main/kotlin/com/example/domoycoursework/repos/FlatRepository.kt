package com.example.domoycoursework.repos

import com.example.domoycoursework.models.Flat
import org.springframework.data.jpa.repository.JpaRepository

interface FlatRepository : JpaRepository<Flat, Long> {
}