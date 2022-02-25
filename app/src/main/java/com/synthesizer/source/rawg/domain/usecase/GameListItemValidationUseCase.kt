package com.synthesizer.source.rawg.domain.usecase

import com.synthesizer.source.rawg.data.remote.GameListItemResponse
import com.synthesizer.source.rawg.utils.isNull
import com.synthesizer.source.rawg.utils.isNullOrZero
import javax.inject.Inject

class GameListItemValidationUseCase @Inject constructor(
    private val platformValidationUseCase: PlatformValidationUseCase
) {
    operator fun invoke(item: GameListItemResponse): Boolean {
        item.apply {
            return !backgroundImage.isNullOrBlank() &&
                    !metacritic.isNullOrZero() &&
                    !rating.isNull() &&
                    !released.isNullOrBlank() &&
                    !genres.isNullOrEmpty() &&
                    !parentPlatforms.orEmpty()
                        .mapNotNull { it.platform }
                        .none { platformValidationUseCase(it) }
        }
    }
}