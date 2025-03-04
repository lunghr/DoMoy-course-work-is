package com.example.domoycoursework.repos

import com.example.domoycoursework.models.Message
import org.springframework.data.jpa.repository.JpaRepository

interface MessageRepository : JpaRepository<Message, Long>{
}