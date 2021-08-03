package com.synthesizer.source.rawg.data.source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.synthesizer.source.rawg.api.api
import com.synthesizer.source.rawg.data.remote.Result

class GamesPagingSource : PagingSource<Int, Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val pageIndex = params.key ?: 1
        return try {
            val response = api.getGames(pageIndex)
            Log.d("synthesizer-source", "load: ${response.toString()}")
            val responseData = mutableListOf<Result>()
            val data = response.results
            responseData.addAll(data)
            val prevKey = if (pageIndex == 1) null else pageIndex - 1

            LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = pageIndex + 1
            )
        } catch (exception: Exception) {
            Log.d("synthesizer-source", "load: ${exception.message}")
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}