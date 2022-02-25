package com.synthesizer.source.rawg.domain.usecase

import com.synthesizer.source.rawg.data.Resource
import com.synthesizer.source.rawg.data.repository.GameDetailRepository
import com.synthesizer.source.rawg.domain.mapper.getGameImage
import com.synthesizer.source.rawg.domain.model.GameImage
import com.synthesizer.source.rawg.utils.map
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class FetchGamesBackgroundImagesUseCase @Inject constructor(private val repository: GameDetailRepository) {
    operator fun invoke(ids: List<Int>) = flow<Resource<List<GameImage>>> {
        val images = ids.map { id ->
            repository.fetchGameDetail(id).map { it.getGameImage() }.body()
        }
        emit(Resource.Success(images.filterNotNull()))
    }.onStart { emit(Resource.Loading(ids.map { GameImage(it, "") })) }
        .catch { emit(Resource.Error(it)) }
}