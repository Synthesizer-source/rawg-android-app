package com.synthesizer.source.rawg.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.synthesizer.source.rawg.api.api
import com.synthesizer.source.rawg.data.remote.GameRemote

class GamesPagingSource : PagingSource<Int, GameRemote>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameRemote> {
        val pageIndex = params.key ?: 1
        return try {
            val response = api.getGames(pageIndex)
            val data = response.results.filter { it.isValid() }
            val responseData = mutableListOf<GameRemote>()
            responseData.addAll(data)
            val prevKey = if (pageIndex == 1) null else pageIndex - 1

            LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = pageIndex + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GameRemote>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}