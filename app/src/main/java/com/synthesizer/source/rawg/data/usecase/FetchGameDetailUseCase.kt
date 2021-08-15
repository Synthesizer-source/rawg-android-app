package com.synthesizer.source.rawg.data.usecase

import com.synthesizer.source.rawg.data.Resource
import com.synthesizer.source.rawg.data.mapper.toDomain
import com.synthesizer.source.rawg.repository.GameDetailRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchGameDetailUseCase @Inject constructor(private val repository: GameDetailRepository) {

    operator fun invoke(id: Int) = flow {
        emit(Resource.Loading())
        val response = repository.fetchGameDetail(id)
        emit(
            when (response.code()) {
                200 -> Resource.Success(response.body()!!.toDomain())
                else -> Resource.Failure(response.errorBody().toString())
            }
        )
    }
}