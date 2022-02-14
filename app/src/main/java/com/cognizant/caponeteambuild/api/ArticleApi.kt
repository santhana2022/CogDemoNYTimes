package com.cognizant.caponeteambuild.api

import com.cognizant.caponeteambuild.BuildConfig
import com.cognizant.caponeteambuild.data.Article
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticleApi {
    @GET("{articleName}.json")
    suspend fun getArticle(
        @Path("articleName") name: String = "home",
        @Query("api-key") apiKey: String = BuildConfig.API_KEY
    ): Article
}