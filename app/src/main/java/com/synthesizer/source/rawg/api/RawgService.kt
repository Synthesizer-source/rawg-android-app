package com.synthesizer.source.rawg.api

import com.synthesizer.source.rawg.data.remote.GameDetailRemote
import com.synthesizer.source.rawg.data.remote.GamesRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RawgService {
    @GET("games")
    suspend fun getGames(
        @Query("page") page: Int,
        @Query("search") search: String,
        @Query("ordering") ordering: String,
        @Query("dates") dates: String
    ): GamesRemote

    @GET("games/{id}")
    suspend fun getGameDetailById(@Path("id") id: Int): Response<GameDetailRemote>
}