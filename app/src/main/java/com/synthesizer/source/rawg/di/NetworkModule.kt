package com.synthesizer.source.rawg.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.synthesizer.source.rawg.data.api.ApiInterceptor
import com.synthesizer.source.rawg.data.api.RawgService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Dns
import okhttp3.OkHttpClient
import okhttp3.logging.LoggingEventListener
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Inet4Address
import java.net.InetAddress
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

class DnsSelector() : Dns {
    override fun lookup(hostname: String): List<InetAddress> {
        return Dns.SYSTEM.lookup(hostname).filter { Inet4Address::class.java.isInstance(it) }
    }
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.rawg.io/api/"

    @Singleton
    private val gson =
        GsonBuilder().setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

    private val eventListenerFactory = LoggingEventListener.Factory()

    @Singleton
    private val client = OkHttpClient().newBuilder()
        .writeTimeout(20, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .dns(DnsSelector())
        .eventListenerFactory(eventListenerFactory)
        .addInterceptor(ApiInterceptor()).build()

    @Singleton
    private val retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()

    @Provides
    @Singleton
    fun provideService(): RawgService = retrofit.create(RawgService::class.java)
}