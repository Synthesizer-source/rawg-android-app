package com.synthesizer.source.rawg.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synthesizer.source.rawg.data.Resource
import com.synthesizer.source.rawg.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewHomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    private var _games = MutableLiveData<List<HomeScreenItem>>()
    val games: LiveData<List<HomeScreenItem>> = _games

    private val gameIds = listOf(
        28,
        30899,
        3990,
        3328,
        22511,
        22509,
        13537,
        3498,
        29642,
        2518,
        4535,
        15002,
        10065,
        58175
    )

    init {
        fetchGames()
    }

    private fun fetchGames() = viewModelScope.launch {
        repository.fetchHomeScreenGames(gameIds).collect {
            when (it) {
                is Resource.Loading -> onLoading()
                is Resource.Success -> onSuccess(it.data)
                else -> onFailure()
            }
        }
    }

    private fun onLoading() {}

    private fun onSuccess(data: HomeScreenItem) {
        if (_games.value.isNullOrEmpty()) _games.value = listOf(data)
        else {
            val gameList = _games.value!!
                .toMutableList()
            gameList.add(data)
            _games.value = gameList.distinctBy { it.id }
        }
    }

    private fun onFailure() {}
}