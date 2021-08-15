package com.synthesizer.source.rawg.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.synthesizer.source.rawg.data.remote.GameListItemResponse
import com.synthesizer.source.rawg.data.repository.GameListRepository
import javax.inject.Inject

class GamesPagingSource @Inject constructor(
    private val repository: GameListRepository,
    private val search: String,
    private val ordering: String,
    private val dates: String
) : PagingSource<Int, GameListItemResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameListItemResponse> {
        val pageIndex = params.key ?: 1
        return try {
            val response =
                repository.fetchGames(
                    page = pageIndex,
                    search = search,
                    ordering = ordering,
                    dates = dates
                )

            val responseData = mutableListOf<GameListItemResponse>()
            responseData.addAll(response.results)
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

    override fun getRefreshKey(state: PagingState<Int, GameListItemResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}