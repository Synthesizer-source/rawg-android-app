package com.synthesizer.source.rawg.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.synthesizer.source.rawg.api.api
import com.synthesizer.source.rawg.data.domain.GameDomain
import com.synthesizer.source.rawg.data.mapper.toDomain

class GamesPagingSource(
    private val search: String,
    private val ordering: String,
    private val dates: String
) : PagingSource<Int, GameDomain>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameDomain> {
        val pageIndex = params.key ?: 1
        return try {
            val response =
                api.getGames(
                    page = pageIndex,
                    search = search,
                    ordering = ordering,
                    dates = dates
                )
            val data = response.results.filter { it.isValid() }.map { it.toDomain() }
            val responseData = mutableListOf<GameDomain>()
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

    override fun getRefreshKey(state: PagingState<Int, GameDomain>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}