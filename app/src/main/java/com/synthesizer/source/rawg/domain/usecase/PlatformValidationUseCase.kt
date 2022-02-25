package com.synthesizer.source.rawg.domain.usecase

import com.synthesizer.source.rawg.data.remote.PlatformResponse
import javax.inject.Inject

class PlatformValidationUseCase @Inject constructor() {
    operator fun invoke(platform: PlatformResponse): Boolean {
        return if (platform.slug.isNullOrBlank()) false
        else {
            platform.slug == "pc" ||
                    platform.slug == "playstation" ||
                    platform.slug == "xbox" ||
                    platform.slug == "nintendo"
        }
    }
}