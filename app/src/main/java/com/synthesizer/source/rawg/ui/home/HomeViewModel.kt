package com.synthesizer.source.rawg.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.synthesizer.source.rawg.data.remote.Result
import com.synthesizer.source.rawg.repository.HomeRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    private var _games = MutableLiveData<PagingData<Result>>()
    val games: LiveData<PagingData<Result>> = _games

    init {
        fetchGames()
    }

    private fun fetchGames() = viewModelScope.launch {
        repository.fetchGames().cachedIn(this).collect {
            _games.value = it
        }
    }
}