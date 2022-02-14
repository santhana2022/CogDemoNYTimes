package com.cognizant.caponeteambuild.repositories

import com.cognizant.caponeteambuild.api.ArticleApi
import com.cognizant.caponeteambuild.data.Article
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ExperimentalCoroutinesApi
class ArticleRepository @Inject constructor(private val articleApi: ArticleApi) {

    suspend fun getArticle(articleName: String): Article {
        return articleApi.getArticle(articleName)
    }
}