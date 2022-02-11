package com.synthesizer.source.rawg.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synthesizer.source.rawg.core.domain.model.Error
import com.synthesizer.source.rawg.core.domain.usecase.GetErrorMessageResUseCase
import com.synthesizer.source.rawg.core.domain.usecase.GetErrorTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var getErrorTypeUseCase: GetErrorTypeUseCase

    @Inject
    lateinit var getErrorMessageResUseCase: GetErrorMessageResUseCase

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
        val errorType = getErrorTypeUseCase(throwable)
        val errorMessageRes = getErrorMessageResUseCase(throwable)
        val error = Error(errorType = errorType, messageRes = errorMessageRes) { retryCallback() }

        viewModelScope.launch {
            _error.emit(error)
        }
    }
}