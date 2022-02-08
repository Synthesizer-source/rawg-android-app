package com.synthesizer.source.rawg.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synthesizer.source.rawg.R
import com.synthesizer.source.rawg.core.domain.Error
import com.synthesizer.source.rawg.core.domain.ErrorType
import com.synthesizer.source.rawg.data.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    private val _error: MutableStateFlow<Error?> = MutableStateFlow(null)
    val error: StateFlow<Error?>
        get() = _error.asStateFlow()

    private val _loading: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val loading: StateFlow<Boolean?>
        get() = _loading.asStateFlow()

    fun loading(isLoading: Boolean = true) {
        viewModelScope.launch {
            _loading.emit(isLoading)
        }
    }

    fun error(throwable: Throwable, callback: (() -> Unit)? = null) {
        if (_error.value != null) return
        val retryCallback = {
            viewModelScope.launch {
                _error.emit(null)
                callback?.invoke()
            }
        }
        val error = when (throwable) {
            is SocketTimeoutException -> Error(
                ErrorType.RETRY,
                R.string.request_timeout
            ) { retryCallback() }
            is UnknownHostException -> Error(
                ErrorType.RETRY,
                R.string.check_your_connection
            ) { retryCallback() }
            is HttpException -> {
                when (throwable.code()) {
                    400 -> Error(ErrorType.NONE, R.string.bad_request)
                    401 -> Error(ErrorType.NONE, R.string.invalid_api_key)
                    403 -> Error(ErrorType.NONE, R.string.bad_request)
                    404 -> Error(ErrorType.RETRY, R.string.not_found) { retryCallback() }
                    else -> Error(ErrorType.RETRY, R.string.unexpected_error) { retryCallback() }
                }
            }
            else -> Error(ErrorType.NONE, R.string.the_server_is_unreachable)
        }

        viewModelScope.launch {
            _error.emit(error)
        }
    }

    fun <T> resource(
        resource: Resource<T>,
        loading: () -> Unit = {},
        success: (data: T) -> Unit,
        retryCallback: () -> Unit = {}
    ) {
        when (resource) {
            is Resource.Loading -> this.loading()
            is Resource.Success -> success(resource.data)
            is Resource.Error -> error(resource.throwable) {
                retryCallback()
            }
        }
    }
}