package com.synthesizer.source.rawg.data.repository

import com.synthesizer.source.rawg.data.api.RawgService
import javax.inject.Inject

class GameListRepository @Inject constructor(private val service: RawgService) {
    suspend fun fetchGames(
        page: Int,
        search: String,
        ordering: String,
        dates: String
    ) = service.getGames(page, search, ordering, dates)
}