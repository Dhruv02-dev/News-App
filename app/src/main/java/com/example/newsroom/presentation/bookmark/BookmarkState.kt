package com.example.newsroom.presentation.bookmark

import com.example.newsroom.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList()
)
