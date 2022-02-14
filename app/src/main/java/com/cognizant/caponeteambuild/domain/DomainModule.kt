package com.cognizant.caponeteambuild.domain

import com.cognizant.caponeteambuild.repositories.ArticleRepository
import com.cognizant.caponeteambuild.repository.local.NewsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @ExperimentalCoroutinesApi
    @Singleton
    fun provideFetchArticleUseCase(articleRepository: ArticleRepository, newsDao: NewsDao) =
        UseCaseFetchArticles(articleRepository, newsDao)
}