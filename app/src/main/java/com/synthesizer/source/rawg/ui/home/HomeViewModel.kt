package com.synthesizer.source.rawg.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synthesizer.source.rawg.data.remote.GamesRemote
import com.synthesizer.source.rawg.repository.HomeRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {
    private var _games = MutableLiveData<GamesRemote>()
    val games: LiveData<GamesRemote> = _games

    init {
        fetchGames()
    }

    private fun fetchGames() = viewModelScope.launch {
        repository.fetchGames().collect {
            _games.value = it
        }
    }
}