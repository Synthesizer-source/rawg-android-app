package com.synthesizer.source.rawg.data

import okio.IOException
import retrofit2.HttpException
import retrofit2.Response

sealed class Resource<out T> {
    class Success<out T>(val data: T) : Resource<T>()
    class Loading<out T>(val data: T? = null) : Resource<T>()
    data class Error(val throwable: Throwable) : Resource<Nothing>()

    companion object {

        inline fun <T> of(f: () -> Response<T>): Resource<T> {
            return try {
                val response = f()
                if (response.code() in 200..299) {
                    val data = response.body()!!
                    Success(data)
                } else {
                    if (response.code() in 400..499) {
                        throw HttpException(response)
                    } else throw IOException(response.message())
                }
            } catch (throwable: Throwable) {
                Error(throwable)
            }
        }
    }
}