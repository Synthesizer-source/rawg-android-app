package com.synthesizer.source.rawg.core.domain.model

import androidx.annotation.StringRes

data class Error(
    val errorType: ErrorType,
    @StringRes val messageRes: Int,
    val callback: () -> Unit = {},
)