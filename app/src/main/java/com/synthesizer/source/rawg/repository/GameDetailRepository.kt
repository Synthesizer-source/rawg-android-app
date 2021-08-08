package com.synthesizer.source.rawg.repository

import android.util.Log
import com.synthesizer.source.rawg.api.api
import com.synthesizer.source.rawg.data.mapper.toDomain
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GameDetailRepository @Inject constructor() {

    fun fetchGameDetail(id: Int) = flow {
        try {
            emit(api.getGameDetailById(id).toDomain())
        } catch (exception: Exception) {
            Log.d("synthesizer-source", "fetchGames: ${exception.message}")
        }
    }
}