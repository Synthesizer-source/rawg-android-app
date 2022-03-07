package com.synthesizer.source.rawg.domain.usecase

import com.synthesizer.source.rawg.core.domain.model.Platforms
import com.synthesizer.source.rawg.data.remote.PlatformResponse
import javax.inject.Inject

class PlatformValidationUseCase @Inject constructor() {
    operator fun invoke(platform: PlatformResponse): Boolean {
        return if (platform.slug.isNullOrBlank()) false
        else {
            platform.slug == Platforms.PC ||
                    platform.slug == Platforms.PLAYSTATION ||
                    platform.slug == Platforms.XBOX ||
                    platform.slug == Platforms.NINTENDO
        }
    }
}