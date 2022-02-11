package com.synthesizer.source.rawg.core.domain.usecase

import androidx.annotation.StringRes
import com.synthesizer.source.rawg.R
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import retrofit2.HttpException

class GetErrorMessageResUseCase @Inject constructor() {
    @StringRes
    operator fun invoke(throwable: Throwable): Int {
        return when (throwable) {
            is SocketTimeoutException ->
                R.string.request_timeout
            is UnknownHostException ->
                R.string.check_your_connection
            is HttpException -> {
                when (throwable.code()) {
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
}