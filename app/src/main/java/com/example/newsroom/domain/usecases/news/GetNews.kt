package com.example.newsroom.domain.usecases.news

import androidx.paging.PagingData
import com.example.newsroom.domain.model.Article
import com.example.newsroom.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNews(
    private val newsRepository: NewsRepository
) {

    operator fun invoke(sources:List<String>): Flow<PagingData<Article>>{
        return newsRepository.getNews(sources = sources)
    }
}