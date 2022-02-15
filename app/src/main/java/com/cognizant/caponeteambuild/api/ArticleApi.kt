package com.cognizant.caponeteambuild.api

import com.cognizant.caponeteambuild.BuildConfig
import com.cognizant.caponeteambuild.data.Article
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticleApi {

    @GET("{articleName}.json")
    suspend fun getArticleV0(
        @Path("articleName") name: String = "home",
        @Query("api-key") apiKey: String = BuildConfig.API_KEY
    ): Article

    @GET("{articleType}")
    suspend fun getArticle(
        @Path("articleType") name: String = "home",
        @Query("api-key") apiKey: String = BuildConfig.API_KEY
    ): Article
}