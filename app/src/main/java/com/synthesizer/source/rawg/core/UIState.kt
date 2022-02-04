package com.synthesizer.source.rawg.core

sealed class UIState<out T : Any> {
    object Loading : UIState<Nothing>()
    data class Error(val message: String) : UIState<Nothing>()
    data class Success<T : Any>(val data: T) : UIState<T>()

    fun <V : Any> map(to: (T) -> V): UIState<V> {
        return when (this) {
            is Error -> Error(message)
            Loading -> Loading
            is Success -> Success(to(data))
        }
    }
}
