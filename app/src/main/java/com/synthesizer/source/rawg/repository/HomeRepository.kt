package com.synthesizer.source.rawg.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.synthesizer.source.rawg.data.source.GamesPagingSource
import com.synthesizer.source.rawg.data.remote.GameRemote
import kotlinx.coroutines.flow.Flow

class HomeRepository {

    fun fetchGames(): Flow<PagingData<GameRemote>> {
        return (Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { GamesPagingSource() }).flow)
    }
}