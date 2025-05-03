package com.example.newsroom.domain.usecases.news

import com.example.newsroom.data.local.NewsDao
import com.example.newsroom.domain.model.Article
import com.example.newsroom.domain.repository.NewsRepository

class UpsertArticle(
   private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(article: Article){
        newsRepository.upsertArticle(article)
    }
}