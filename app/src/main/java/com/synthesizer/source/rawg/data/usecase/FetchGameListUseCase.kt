package com.synthesizer.source.rawg.data.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.filter
import androidx.paging.map
import com.synthesizer.source.rawg.data.Resource
import com.synthesizer.source.rawg.data.mapper.toDomain
import com.synthesizer.source.rawg.data.source.GamesPagingSource
import com.synthesizer.source.rawg.repository.GameListRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchGameListUseCase @Inject constructor(private val repository: GameListRepository) {

    operator fun invoke(
        search: String,
        ordering: String,
        dates: String
    ) = flow {
        emit(Resource.Loading())
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
    }
}