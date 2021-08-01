package com.synthesizer.source.rawg.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.synthesizer.source.rawg.api.api
import com.synthesizer.source.rawg.data.remote.GamesRemote
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private var _games = MutableLiveData<GamesRemote>()
    val games: LiveData<GamesRemote> = _games

    init {
        fetchGames()
    }

    private fun fetchGames(){
        api.getGames().enqueue(object : Callback<GamesRemote> {
            override fun onResponse(call: Call<GamesRemote>, response: Response<GamesRemote>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _games.value = it
                    }
                }
            }

            override fun onFailure(call: Call<GamesRemote>, t: Throwable) {
                Log.d("synthesizer-source", "onResponse: ${t.message}")
            }
        })
    }
}