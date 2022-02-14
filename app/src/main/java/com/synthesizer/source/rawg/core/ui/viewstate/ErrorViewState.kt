package com.synthesizer.source.rawg.core.ui.viewstate

import androidx.annotation.StringRes
import com.synthesizer.source.rawg.R
import com.synthesizer.source.rawg.core.domain.model.Error
import com.synthesizer.source.rawg.core.domain.model.ErrorType
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import retrofit2.HttpException

class ErrorViewState(private val error: Error) {
    @StringRes
    fun getErrorMessage(): Int {
        return when (error.throwable) {
            is SocketTimeoutException ->
                R.string.request_timeout
            is UnknownHostException ->
                R.string.check_your_connection
            is HttpException -> {
                when (error.throwable.code()) {
                    400 -> R.string.bad_request
                    401 -> R.string.invalid_api_key
                    403 -> R.string.bad_request
                    404 -> R.string.not_found
                    else -> R.string.unexpected_error
                }
            }
            else -> R.string.the_server_is_unreachable
        }
    }

    fun getErrorType(): ErrorType {
        return when (error.throwable) {
            is SocketTimeoutException -> ErrorType.RETRY
            is UnknownHostException -> ErrorType.RETRY
            is HttpException -> {
                when (error.throwable.code()) {
                    400 -> ErrorType.RETRY
                    401 -> ErrorType.NONE
                    403 -> ErrorType.NONE
                    404 -> ErrorType.RETRY
                    else -> ErrorType.NONE
                }
            }
            else -> ErrorType.NONE
        }
    }
}