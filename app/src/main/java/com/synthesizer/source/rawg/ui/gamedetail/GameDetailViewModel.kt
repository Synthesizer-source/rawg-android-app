package com.synthesizer.source.rawg.ui.gamedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synthesizer.source.rawg.data.remote.GameDetailRemote
import com.synthesizer.source.rawg.repository.GameDetailRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GameDetailViewModel(
    private val gameId: Int,
    private val repository: GameDetailRepository
) :
    ViewModel() {
    private var _gameDetail = MutableLiveData<GameDetailRemote>()
    val gameDetail: LiveData<GameDetailRemote> = _gameDetail

    init {
        fetchGameDetail()
    }

    private fun fetchGameDetail() = viewModelScope.launch {
        repository.fetchGameDetail(gameId).collect {
            _gameDetail.value = it
        }
    }
}