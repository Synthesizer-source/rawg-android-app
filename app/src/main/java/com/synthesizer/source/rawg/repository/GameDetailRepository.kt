package com.synthesizer.source.rawg.repository

import com.synthesizer.source.rawg.api.api
import javax.inject.Inject

class GameDetailRepository @Inject constructor() {

    suspend fun fetchGameDetail(id: Int) = api.getGameDetailById(id)

    suspend fun fetchGameScreenshots(id: Int) = api.getGameScreenshots(id)
}