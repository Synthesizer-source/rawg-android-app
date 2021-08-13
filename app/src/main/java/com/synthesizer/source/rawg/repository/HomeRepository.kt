package com.synthesizer.source.rawg.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.synthesizer.source.rawg.api.api
import com.synthesizer.source.rawg.data.ErrorHolder
import com.synthesizer.source.rawg.data.Resource
import com.synthesizer.source.rawg.data.mapper.toHomeScreenItem
import com.synthesizer.source.rawg.data.source.GamesPagingSource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepository @Inject constructor() {

    fun fetchGames(
        search: String,
        ordering: String,
        dates: String
    ) = flow {
        emit(Resource.Loading())

        try {
            val data = (Pager(
                config = PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = true
                ),
                pagingSourceFactory = { GamesPagingSource(search, ordering, dates) }).flow)

            emit(Resource.Success(data))
        } catch (exception: Exception) {
        }
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