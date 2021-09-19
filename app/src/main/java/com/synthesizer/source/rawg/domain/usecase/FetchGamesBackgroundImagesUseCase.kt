package com.synthesizer.source.rawg.domain.usecase

import com.synthesizer.source.rawg.data.Resource
import com.synthesizer.source.rawg.data.repository.GameDetailRepository
import com.synthesizer.source.rawg.domain.model.GameImage
import com.synthesizer.source.rawg.utils.map
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class FetchGamesBackgroundImagesUseCase @Inject constructor(private val repository: GameDetailRepository) {
    operator fun invoke(ids: List<Int>) = flow {
        ids.forEach {
            emit(Resource.of {
                repository.fetchGameDetail(it).map { data ->
                    GameImage(data.id, data.background_image)
                }
            })
        }
    }.onStart {
        emit(Resource.Loading())
    }
}