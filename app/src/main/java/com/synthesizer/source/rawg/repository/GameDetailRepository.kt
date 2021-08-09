package com.synthesizer.source.rawg.repository

import com.synthesizer.source.rawg.api.api
import com.synthesizer.source.rawg.data.ErrorHolder.*
import com.synthesizer.source.rawg.data.Resource
import com.synthesizer.source.rawg.data.mapper.toDomain
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
}