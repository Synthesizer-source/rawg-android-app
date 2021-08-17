package com.synthesizer.source.rawg.domain.usecase

import com.synthesizer.source.rawg.data.Resource
import com.synthesizer.source.rawg.data.repository.GameDetailRepository
import com.synthesizer.source.rawg.domain.model.GameImage
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchGamesBackgroundImagesUseCase @Inject constructor(private val repository: GameDetailRepository) {
    operator fun invoke(ids: List<Int>) = flow {
        emit(Resource.Loading())
        ids.forEach {
            val response = repository.fetchGameDetail(it)
            val data = response.body()!!
            emit(
                when (response.code()) {
                    200 -> Resource.Success(
                        GameImage(
                            id = data.id,
                            imageUrl = data.background_image
                        )
                    )
                    else -> Resource.Failure(response.errorBody()!!.string())
                }
            )
        }
    }
}