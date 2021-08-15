package com.synthesizer.source.rawg.data.repository

import com.synthesizer.source.rawg.data.api.RawgService
import javax.inject.Inject

class GameDetailRepository @Inject constructor(private val service: RawgService) {

    suspend fun fetchGameDetail(id: Int) = service.getGameDetailById(id)

    suspend fun fetchGameScreenshots(id: Int) = service.getGameScreenshots(id)
}