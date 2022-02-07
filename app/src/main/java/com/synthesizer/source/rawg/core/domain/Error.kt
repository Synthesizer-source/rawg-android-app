package com.synthesizer.source.rawg.core.domain

import androidx.annotation.StringRes

enum class ErrorType {
    RETRY, NONE
}

data class Error(
    val errorType: ErrorType,
    @StringRes val messageRes: Int,
    val callback: () -> Unit = {},
)