package com.synthesizer.source.rawg.api

import com.synthesizer.source.rawg.data.remote.GamesRemote
import retrofit2.Call
import retrofit2.http.GET

interface RawgService {
    @GET("games")
    fun getGames(): Call<GamesRemote>
}