package com.synthesizer.source.rawg.api

import com.synthesizer.source.rawg.data.remote.GameDetailRemote
import com.synthesizer.source.rawg.data.remote.GamesRemote
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RawgService {
    @GET("games")
    fun getGames(): Call<GamesRemote>

    @GET("games/{id}")
    fun getGameDetailById(@Path("id") id: Int): Call<GameDetailRemote>
}