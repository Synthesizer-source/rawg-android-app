package com.synthesizer.source.rawg.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.synthesizer.source.rawg.api.api
import com.synthesizer.source.rawg.data.ErrorHolder
import com.synthesizer.source.rawg.data.Resource
import com.synthesizer.source.rawg.data.mapper.toHomeScreenItem
import com.synthesizer.source.rawg.data.remote.GameRemote
import com.synthesizer.source.rawg.data.source.GamesPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepository @Inject constructor() {

    fun fetchGames(): Flow<PagingData<GameRemote>> {
        return (Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { GamesPagingSource() }).flow)
    }

    fun fetchHomeGame(id: Int) = flow {
        emit(Resource.Loading())
        val response = try {
            api.getGameDetailById(id)
        } catch (exception: Exception) {
            null
        }

        emit(
            when (response?.code()) {
                null -> Resource.Failure(ErrorHolder.NetworkError)
                200 -> Resource.Success(response.body()!!.toHomeScreenItem())
                401 -> Resource.Failure(ErrorHolder.UnauthorizedError)
                else -> Resource.Failure(ErrorHolder.UnExpectedError)
            }
        )
    }

    fun fetchHomeGameWithList(ids: List<Int>) = flow {
        emit(Resource.Loading())
        val response = ids.map {
            try {
                api.getGameDetailById(it)
            } catch (exception: Exception) {
                null
            }

        }
        response.map {
            emit(
                when (it?.code()) {
                    null -> Resource.Failure(ErrorHolder.NetworkError)
                    200 -> Resource.Success(it.body()!!.toHomeScreenItem())
                    401 -> Resource.Failure(ErrorHolder.UnauthorizedError)
                    else -> Resource.Failure(ErrorHolder.UnExpectedError)
                }
            )
        }
    }
}