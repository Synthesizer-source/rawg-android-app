package com.synthesizer.source.rawg.domain.usecase

import com.synthesizer.source.rawg.data.Resource
import com.synthesizer.source.rawg.data.repository.GameDetailRepository
import com.synthesizer.source.rawg.domain.mapper.toDomain
import com.synthesizer.source.rawg.utils.map
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class FetchGameDetailUseCase @Inject constructor(private val repository: GameDetailRepository) {

    operator fun invoke(id: Int) = flow {
        emit(Resource.of { repository.fetchGameDetail(id).map { it.toDomain() } })
    }.onStart {
        emit(Resource.Loading())
    }
}