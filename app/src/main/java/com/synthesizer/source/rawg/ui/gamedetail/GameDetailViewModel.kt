package com.synthesizer.source.rawg.ui.gamedetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.synthesizer.source.rawg.api.api
import com.synthesizer.source.rawg.data.remote.GameDetailRemote
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameDetailViewModel(private val gameId: Int) : ViewModel() {
    private var _gameDetail = MutableLiveData<GameDetailRemote>()
    val gameDetail: LiveData<GameDetailRemote> = _gameDetail

    init {
        fetchGameDetail()
    }

    private fun fetchGameDetail() {
        api.getGameDetailById(gameId).enqueue(object : Callback<GameDetailRemote> {
            override fun onResponse(
                call: Call<GameDetailRemote>,
                response: Response<GameDetailRemote>
            ) {
                if (response.isSuccessful) {
                    _gameDetail.value = response.body()!!
                }
            }

            override fun onFailure(call: Call<GameDetailRemote>, t: Throwable) {
                Log.d("synthesizer-source", "onFailure: ${t.message}")
            }
        })
    }
}