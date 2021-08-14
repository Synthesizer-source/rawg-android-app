package com.synthesizer.source.rawg.repository

import android.util.Log
import com.synthesizer.source.rawg.api.api
import com.synthesizer.source.rawg.data.ErrorHolder.*
import com.synthesizer.source.rawg.data.Resource
import com.synthesizer.source.rawg.data.mapper.toDomain
import com.synthesizer.source.rawg.data.remote.ShortScreenshot
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GameDetailRepository @Inject constructor() {

    fun fetchGameDetail(id: Int) = flow {
        emit(Resource.Loading())
        val response = try {
            api.getGameDetailById(id)
        } catch (exception: Exception) {
            null
        }

        emit(
            when (response?.code()) {
                null -> Resource.Failure(NetworkError)
                200 -> Resource.Success(response.body()!!.toDomain())
                401 -> Resource.Failure(UnauthorizedError)
                else -> Resource.Failure(UnExpectedError)
            }
        )
    }

    fun fetchGameScreenshots(id: Int) = flow {
        emit(Resource.Loading())
        val response = try {
            api.getGameScreenshots(id)
        } catch (exception: Exception) {
            null
        }
        Log.d("synthesizer-source", "fetchGameScreenshots: ${response?.body()}")

        emit(
            when (response?.code()) {
                null -> Resource.Failure(NetworkError)
                200 -> Resource.Success(response.body()!!.results.map {
                    ShortScreenshot(
                        id = it.id,
                        image = it.image
                    )
                })
                401 -> Resource.Failure(UnauthorizedError)
                else -> Resource.Failure(UnExpectedError)
            }
        )
    }
}