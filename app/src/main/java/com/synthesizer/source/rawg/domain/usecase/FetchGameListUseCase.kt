package com.synthesizer.source.rawg.domain.usecase

import androidx.paging.*
import com.synthesizer.source.rawg.data.Resource
import com.synthesizer.source.rawg.data.repository.GameListRepository
import com.synthesizer.source.rawg.data.source.GamesPagingSource
import com.synthesizer.source.rawg.domain.mapper.toDomain
import com.synthesizer.source.rawg.domain.model.GameListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class FetchGameListUseCase @Inject constructor(private val repository: GameListRepository) {

    operator fun invoke(
        search: String,
        ordering: String,
        dates: String
    ) = flow<Resource<Flow<PagingData<GameListItem>>>> {

        try {
            val data = (Pager(
                config = PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = true
                ),
                pagingSourceFactory = {
                    GamesPagingSource(
                        repository,
                        search,
                        ordering,
                        dates
                    )
                }).flow).map { pagingData ->
                pagingData.filter {
                    it.isValid()
                }.map {
                    it.toDomain()
                }
            }

            emit(Resource.Success(data))
        } catch (exception: Exception) {
        }
    }.onStart { emit(Resource.Loading()) }
}