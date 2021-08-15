package com.synthesizer.source.rawg.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.synthesizer.source.rawg.data.api.ApiInterceptor
import com.synthesizer.source.rawg.data.api.RawgService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.rawg.io/api/"

    @Singleton
    private val gson =
        GsonBuilder().setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

    @Singleton
    private val client = OkHttpClient().newBuilder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(ApiInterceptor()).build()

    @Singleton
    private val retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()

    @Provides
    @Singleton
    fun provideService(): RawgService = retrofit.create(RawgService::class.java)
}