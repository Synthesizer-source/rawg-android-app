package com.synthesizer.source.rawg.repository

import com.synthesizer.source.rawg.api.api
import javax.inject.Inject

class GameListRepository @Inject constructor() {
    suspend fun fetchGames(
        page: Int,
        search: String,
        ordering: String,
        dates: String
    ) = api.getGames(page, search, ordering, dates)
}