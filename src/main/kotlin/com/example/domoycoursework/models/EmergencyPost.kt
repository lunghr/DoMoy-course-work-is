package com.example.domoycoursework.models

import com.example.domoycoursework.models.enums.SeverityLevel
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity


@Entity
@DiscriminatorValue("EMERGENCY")
class EmergencyPost(
    title: String,
    content: String,
    author: User,

    var severityLevel: SeverityLevel,
    var allowComments: Boolean = false
) : FeedItem(title = title, content = content, author = author)