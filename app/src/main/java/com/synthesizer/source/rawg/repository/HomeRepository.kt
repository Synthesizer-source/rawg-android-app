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

    fun fetchHomeScreenGames(ids: List<Int>) = flow {
        emit(Resource.Loading())
        ids.forEach {
            try {
                val data = api.getGameDetailById(it)
                emit(
                    when (data.code()) {
                        null -> Resource.Failure(ErrorHolder.NetworkError)
                        200 -> Resource.Success(data.body()!!.toHomeScreenItem())
                        401 -> Resource.Failure(ErrorHolder.UnauthorizedError)
                        else -> Resource.Failure(ErrorHolder.UnExpectedError)
                    }
                )
            } catch (exception: Exception) {
            }
        }
    }
}