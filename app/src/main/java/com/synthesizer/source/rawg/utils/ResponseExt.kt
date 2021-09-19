package com.synthesizer.source.rawg.utils

import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

fun <T, V> Response<T>.map(transform: (T) -> V): Response<V> {

    val newBody = body()?.let {
        transform(it)
    }
    
    return if (newBody != null) {
        Response.success(newBody)
    } else {
        Response.error(
            code(),
            errorBody() ?: createUnknownError()
        )
    }
}

private fun createUnknownError(): ResponseBody {
    val jsonObject = JsonObject()
    jsonObject.addProperty("detail", "Unknown error")
    return jsonObject.toString()
        .toResponseBody("application/json; charset=utf-8".toMediaType())
}