package com.synthesizer.source.rawg.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.synthesizer.source.rawg.data.remote.GameRemote
import com.synthesizer.source.rawg.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    private var _games = MutableLiveData<PagingData<GameRemote>>()
    val games: LiveData<PagingData<GameRemote>> = _games

    init {
        fetchGames()
    }

    private fun fetchGames() = viewModelScope.launch {
        repository.fetchGames().cachedIn(this).collect {
            _games.value = it
        }
    }
}