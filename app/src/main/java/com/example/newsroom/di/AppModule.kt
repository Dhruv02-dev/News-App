package com.example.newsroom.di

import android.app.Application
import androidx.room.Room
import com.example.newsroom.data.local.NewsDao
import com.example.newsroom.data.local.NewsDatabase
import com.example.newsroom.data.local.NewsTypeConverter
import com.example.newsroom.data.manager.LocalUserManagerImpl
import com.example.newsroom.data.remote.dto.NewsApi
import com.example.newsroom.data.repository.NewsRepositoryImpl
import com.example.newsroom.domain.manager.LocalUserManager
import com.example.newsroom.domain.repository.NewsRepository
import com.example.newsroom.domain.usecases.app_entry.AppEntryUseCases
import com.example.newsroom.domain.usecases.app_entry.ReadAppEntry
import com.example.newsroom.domain.usecases.app_entry.SaveAppEntry
import com.example.newsroom.domain.usecases.news.DeleteArticle
import com.example.newsroom.domain.usecases.news.GetNews
import com.example.newsroom.domain.usecases.news.NewsUseCases
import com.example.newsroom.domain.usecases.news.SearchNews
import com.example.newsroom.domain.usecases.news.SelectArticle
import com.example.newsroom.domain.usecases.news.SelectArticles
import com.example.newsroom.domain.usecases.news.UpsertArticle
import com.example.newsroom.util.Constants.BASE_URL
import com.example.newsroom.util.Constants.NEWS_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ): LocalUserManager = LocalUserManagerImpl(application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    ) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi:: class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi,
        newsDao: NewsDao
    ): NewsRepository = NewsRepositoryImpl(newsApi, newsDao)

    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository,
        newsDao: NewsDao
    ): NewsUseCases{
        return NewsUseCases(
            getNews = GetNews(newsRepository),
            searchNews = SearchNews(newsRepository),
            upsertArticle = UpsertArticle(newsRepository),
            selectArticles = SelectArticles(newsRepository),
            deleteArticle = DeleteArticle(newsRepository),
            selectArticle = SelectArticle(newsRepository)
        )
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ): NewsDatabase{
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = NEWS_DATABASE_NAME
        ).addTypeConverter(NewsTypeConverter())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewsDatabase
    ): NewsDao = newsDatabase.newsDao

}