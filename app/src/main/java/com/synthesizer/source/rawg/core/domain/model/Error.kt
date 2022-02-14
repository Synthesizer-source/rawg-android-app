package com.synthesizer.source.rawg.core.domain.model

data class Error(
    val throwable: Throwable,
    val callback: () -> Unit = {},
)