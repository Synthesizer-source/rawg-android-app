package com.synthesizer.source.rawg.data.api

import com.synthesizer.source.rawg.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url
            .newBuilder()
            .addQueryParameter("key", BuildConfig.api_key)
            .build()
        return chain.proceed(request.newBuilder().url(url).build())
    }
}