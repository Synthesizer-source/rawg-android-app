package com.synthesizer.source.rawg.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.synthesizer.source.rawg.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    data class ResourceError(val errorCode: Int, val errorBody: ResponseBody?)

    // Error

    private var _badRequestError = MutableLiveData<Event<ResourceError>>()
    val badRequestError: LiveData<Event<ResourceError>> = _badRequestError

    private var _unauthorizedError = MutableLiveData<Event<ResourceError>>()
    val unauthorizedError: LiveData<Event<ResourceError>> = _unauthorizedError

    private var _notFoundError = MutableLiveData<Event<ResourceError>>()
    val notFoundError: LiveData<Event<ResourceError>> = _notFoundError

    private var _unknownError = MutableLiveData<Event<ResourceError>>()
    val unknownError: LiveData<Event<ResourceError>> = _unknownError

    // Exceptions

    private var _timeoutException = MutableLiveData<Event<Throwable>>()
    val timeoutException: LiveData<Event<Throwable>> = _timeoutException

    private var _unknownHostException = MutableLiveData<Event<Throwable>>()
    val unknownHostException: LiveData<Event<Throwable>> = _unknownHostException

    private var _serverException = MutableLiveData<Event<Throwable>>()
    val serverException: LiveData<Event<Throwable>> = _serverException

    protected fun error(errorCode: Int, errorBody: ResponseBody?) {
        val error = Event(ResourceError(errorCode, errorBody))
        when (errorCode) {
            400 -> _badRequestError.value = error
            401 -> _unauthorizedError.value = error
            403 -> _unauthorizedError.value = error
            404 -> _notFoundError.value = error
            else -> _unknownError.value = error
        }
    }

    protected fun exception(throwable: Throwable) {
        val exception = Event(throwable)
        when (throwable) {
            is SocketTimeoutException -> _timeoutException.value = exception
            is UnknownHostException -> _unknownHostException.value = exception
            is HttpException -> error(throwable.code(), throwable.response()?.errorBody())
            else -> _serverException.value = exception
        }
    }
}