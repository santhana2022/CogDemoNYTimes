package com.cognizant.caponeteambuild.api

import com.cognizant.caponeteambuild.BuildConfig
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 *  Retrofit Networking configuration module
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @RetrofitClient
    @Singleton
    fun getRetrofitClient(): Retrofit {
        val builder = GsonBuilder().setLenient().create()
        // Retrofit builder with basic configuration
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(builder))
            .baseUrl(BuildConfig.BASE_URL)
            .client(buildHttpClient())
            .build()
    }

    private fun buildHttpClient(timeout: Long = 30): OkHttpClient {
        return getHttpClientBuilder(timeout).build()
    }

    private fun getHttpClientBuilder(timeout: Long = 30): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(httpLoggingInterceptor)
        }

        return builder
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
    }

    // API related dependencies
    @Provides
    fun articleApi(@RetrofitClient retrofit: Retrofit): ArticleApi =
        retrofit.create(ArticleApi::class.java)

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitClient
