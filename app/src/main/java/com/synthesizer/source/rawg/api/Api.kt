package com.synthesizer.source.rawg.api

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.synthesizer.source.rawg.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val api get() = _api!!
private var _api: RawgService? = null
private const val BASE_URL = "https://api.rawg.io/api/"

private val client = OkHttpClient().newBuilder()
    .connectTimeout(20, TimeUnit.SECONDS)
    .writeTimeout(20, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .addInterceptor(ApiInterceptor()).build()

fun generateApi() {
    if (_api != null) return

    val gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    _api = retrofit.create(RawgService::class.java)
}

private class ApiInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url
            .newBuilder()
            .addQueryParameter("key", BuildConfig.api_key)
            .build()
        return chain.proceed(request.newBuilder().url(url).build())
    }
}