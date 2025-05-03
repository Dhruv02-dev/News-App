package com.example.newsroom.presentation.details.components

import com.example.newsroom.domain.model.Article
import com.example.newsroom.domain.usecases.news.SelectArticle

sealed class DetailsEvent {


   data class UpsertDeleteArticle(val article: Article) : DetailsEvent()

    object RemoveSideEffect : DetailsEvent()
}