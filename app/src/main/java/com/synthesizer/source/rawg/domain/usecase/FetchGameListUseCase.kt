package com.synthesizer.source.rawg.domain.usecase

import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import com.synthesizer.source.rawg.core.domain.model.SearchParams
import com.synthesizer.source.rawg.data.repository.GameListRepository
import com.synthesizer.source.rawg.domain.mapper.toDomain
import com.synthesizer.source.rawg.domain.model.GameListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchGameListUseCase @Inject constructor(
    private val repository: GameListRepository,
    private val gameListItemValidationUseCase: GameListItemValidationUseCase
) {

    operator fun invoke(
        searchParams: SearchParams
    ): Flow<PagingData<GameListItem>> {
        return repository.fetchGames(
            search = searchParams.search,
            ordering = searchParams.ordering,
            dates = searchParams.dates
        ).map { pagingData ->
            pagingData.filter {
                gameListItemValidationUseCase(it)
            }.map {
                it.toDomain()
            }
        }
    }
}