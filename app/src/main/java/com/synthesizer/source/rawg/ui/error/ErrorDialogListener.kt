package com.synthesizer.source.rawg.ui.error

interface RetryableErrorDialogListener : ErrorDialogListener {
    fun retry()
}

interface ErrorDialogListener {
    fun cancel()
}