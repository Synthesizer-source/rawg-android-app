package com.synthesizer.source.rawg.api

import com.synthesizer.source.rawg.data.remote.GameDetailRemote
import com.synthesizer.source.rawg.data.remote.GamesRemote
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RawgService {
    @GET("games")
    suspend fun getGames(@Query("page") page: Int): GamesRemote

    @GET("games/{id}")
    suspend fun getGameDetailById(@Path("id") id: Int): GameDetailRemote
}