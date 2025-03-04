package com.example.domoycoursework.repos

import com.example.domoycoursework.models.Application
import org.springframework.data.jpa.repository.JpaRepository

interface ApplicationRepository : JpaRepository<Application, Long>{
    fun findApplicationById(id: Long): Application?

}