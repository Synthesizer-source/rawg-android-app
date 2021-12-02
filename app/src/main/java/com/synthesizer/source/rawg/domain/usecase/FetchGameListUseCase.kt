package com.synthesizer.source.rawg.domain.usecase

import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import com.synthesizer.source.rawg.data.repository.GameListRepository
import com.synthesizer.source.rawg.domain.mapper.toDomain
import com.synthesizer.source.rawg.domain.model.GameListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchGameListUseCase @Inject constructor(private val repository: GameListRepository) {

    operator fun invoke(
        search: String,
        ordering: String,
        dates: String
    ): Flow<PagingData<GameListItem>> {
        return repository.fetchGames(search, ordering, dates).map { pagingData ->
            pagingData.filter {
                it.isValid()
            }.map {
                it.toDomain()
            }
        }
    }
}