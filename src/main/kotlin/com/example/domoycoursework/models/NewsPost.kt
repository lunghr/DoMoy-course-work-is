package com.example.domoycoursework.models

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity


@Entity
@DiscriminatorValue("NEWS")
class NewsPost(
    title: String,
    content: String,
    author: User,

    var attachment: String? = null,
    var allowComments: Boolean = true,
    var showInMainFeed: Boolean = true,
) : FeedItem(title = title, content = content, author = author)