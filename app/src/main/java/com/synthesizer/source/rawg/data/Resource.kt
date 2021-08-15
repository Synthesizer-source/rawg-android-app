package com.synthesizer.source.rawg.data

sealed class Resource<out T> {
    class Success<out T>(val data: T) : Resource<T>()
    class Loading<out T>(val data: T? = null) : Resource<T>()
    class Failure(val message: String?) : Resource<Nothing>()
}