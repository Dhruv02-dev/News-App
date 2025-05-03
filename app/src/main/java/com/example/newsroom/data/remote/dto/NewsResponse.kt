package com.example.newsroom.data.remote.dto

import com.example.newsroom.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)
