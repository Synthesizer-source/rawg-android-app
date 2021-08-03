package com.synthesizer.source.rawg.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.synthesizer.source.rawg.data.source.GamesPagingSource
import com.synthesizer.source.rawg.data.remote.Result
import kotlinx.coroutines.flow.Flow

class HomeRepository {

    fun fetchGames(): Flow<PagingData<Result>> {
        return (Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { GamesPagingSource() }).flow)
    }
}