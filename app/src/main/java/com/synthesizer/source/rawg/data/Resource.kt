package com.synthesizer.source.rawg.data

import okhttp3.ResponseBody
import okio.IOException
import retrofit2.Response

sealed class Resource<out T> {
    class Success<out T>(val data: T) : Resource<T>()
    class Loading<out T>(val data: T? = null) : Resource<T>()

    sealed class Failure : Resource<Nothing>() {
        data class Exception(val throwable: Throwable) : Failure()
        data class Error(val errorCode: Int, val errorBody: ResponseBody? = null) : Failure()
    }

    companion object {

        inline fun <T> of(f: () -> Response<T>): Resource<T> {
            return try {
                val response = f()
                if (response.code() in 200..299) {
                    val data = response.body()!!
                    Success(data)
                } else {
                    if (response.code() in 400..499) {
                        Failure.Error(response.code(), response.errorBody())
                    } else throw IOException(response.message())
                }
            } catch (throwable: Throwable) {
                Failure.Exception(throwable)
            }
        }
    }
}