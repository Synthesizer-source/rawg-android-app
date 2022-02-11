package com.synthesizer.source.rawg.core.domain.usecase

import com.synthesizer.source.rawg.core.domain.model.ErrorType
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import retrofit2.HttpException

class GetErrorTypeUseCase @Inject constructor() {
    operator fun invoke(throwable: Throwable): ErrorType {
        return when (throwable) {
            is SocketTimeoutException -> ErrorType.RETRY
            is UnknownHostException -> ErrorType.RETRY
            is HttpException -> {
                when (throwable.code()) {
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