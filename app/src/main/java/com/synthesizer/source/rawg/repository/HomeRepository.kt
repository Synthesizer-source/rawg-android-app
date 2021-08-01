package com.synthesizer.source.rawg.repository

import android.util.Log
import com.synthesizer.source.rawg.api.api
import kotlinx.coroutines.flow.flow

class HomeRepository {

    fun fetchGames() = flow {
        try {
            emit(api.getGames())
        } catch (exception: Exception) {
            Log.d("synthesizer-source", "fetchGames: ${exception.message}")
        }
    }
}