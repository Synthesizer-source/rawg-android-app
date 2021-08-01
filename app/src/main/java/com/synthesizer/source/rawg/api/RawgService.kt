package com.synthesizer.source.rawg.api

import com.synthesizer.source.rawg.data.remote.GameDetailRemote
import com.synthesizer.source.rawg.data.remote.GamesRemote
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RawgService {
    @GET("games")
    suspend fun getGames(): GamesRemote

    @GET("games/{id}")
    suspend fun getGameDetailById(@Path("id") id: Int): GameDetailRemote
}