package com.example.domoycoursework.repos

import com.example.domoycoursework.models.ResidentialComplex
import org.springframework.data.jpa.repository.JpaRepository

interface ResidentalComplexRepository: JpaRepository<ResidentialComplex, Long> {
}