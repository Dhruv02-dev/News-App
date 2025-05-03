package com.example.newsroom.domain.usecases.news

import com.example.newsroom.data.local.NewsDao
import com.example.newsroom.domain.model.Article
import com.example.newsroom.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SelectArticles(
    private val newsRepository: NewsRepository
) {

    operator fun invoke():Flow<List<Article>>{
        return newsRepository.selectArticles()
    }
}