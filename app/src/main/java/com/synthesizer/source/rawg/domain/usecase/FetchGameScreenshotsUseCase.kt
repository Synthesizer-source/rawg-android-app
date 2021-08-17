package com.synthesizer.source.rawg.domain.usecase

import com.synthesizer.source.rawg.data.Resource
import com.synthesizer.source.rawg.data.repository.GameDetailRepository
import com.synthesizer.source.rawg.domain.model.GameImage
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchGameScreenshotsUseCase @Inject constructor(private val repository: GameDetailRepository) {

    operator fun invoke(id: Int) = flow {
        emit(Resource.Loading())
        val response = repository.fetchGameScreenshots(id)
        emit(
            if (response.code() == 200) {
                Resource.Success(response.body()!!.results.map {
                    GameImage(
                        it.id,
                        it.image
                    )
                })
            } else Resource.Failure(response.errorBody()?.toString())
        )
    }
}