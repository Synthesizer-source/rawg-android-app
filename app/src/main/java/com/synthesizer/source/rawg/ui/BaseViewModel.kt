package com.synthesizer.source.rawg.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synthesizer.source.rawg.R
import com.synthesizer.source.rawg.core.domain.Error
import com.synthesizer.source.rawg.core.domain.ErrorType
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
    val error: StateFlow<Error?> = _error.asStateFlow()

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
}