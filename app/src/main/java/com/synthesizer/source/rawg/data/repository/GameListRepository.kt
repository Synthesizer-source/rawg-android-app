package com.synthesizer.source.rawg.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.synthesizer.source.rawg.data.api.RawgService
import com.synthesizer.source.rawg.data.source.GamesPagingSource
import javax.inject.Inject

class GameListRepository @Inject constructor(private val service: RawgService) {

    fun fetchGames(
        search: String,
        ordering: String,
        dates: String
    ) = Pager(
        PagingConfig(
            pageSize = 20
        )
    ) {
        GamesPagingSource(
            service,
            search,
            ordering,
            dates
        )
    }.flow
}