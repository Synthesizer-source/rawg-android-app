package com.synthesizer.source.rawg.domain.usecase

import com.synthesizer.source.rawg.data.Resource
import com.synthesizer.source.rawg.data.repository.GameDetailRepository
import com.synthesizer.source.rawg.domain.model.GameImage
import com.synthesizer.source.rawg.utils.map
import javax.inject.Inject
import kotlinx.coroutines.flow.flow

class FetchGameScreenshotsUseCase @Inject constructor(private val repository: GameDetailRepository) {

    operator fun invoke(id: Int) = flow {
        emit(Resource.of {
            repository.fetchGameScreenshots(id).map {
                it.results.map { screenshot ->
                    GameImage(screenshot.id, screenshot.image)
                }
            }
        })
    }
}