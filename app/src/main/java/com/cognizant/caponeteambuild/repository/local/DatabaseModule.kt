package com.cognizant.caponeteambuild.repository.local

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @ExperimentalSerializationApi
    fun provideNewsDao(newsDatabase: NewsDatabase): NewsDao = newsDatabase.newsDao()

    @Provides
    @ExperimentalSerializationApi
    fun provideNewsDatabase(application: Application): NewsDatabase =
        NewsDatabase.getDatabase(application.applicationContext)
}