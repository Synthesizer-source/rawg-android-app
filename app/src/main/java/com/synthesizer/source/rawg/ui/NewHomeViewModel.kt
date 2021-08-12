package com.synthesizer.source.rawg.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synthesizer.source.rawg.data.Resource
import com.synthesizer.source.rawg.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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
//        fetchGames()
        fetchGames2()
    }

    private fun fetchGames() = viewModelScope.launch {

        val objects = mutableListOf<HomeScreenItem>()
        val defferedList = gameIds.map {
            viewModelScope.async {
                repository.fetchHomeGame(it)
            }
        }

        val responses = defferedList.awaitAll()
        responses.map {
            it.collect { response ->
                if (response is Resource.Success) objects.add(response.data)
            }
        }

        _games.value = objects
    }

    private fun fetchGames2() = viewModelScope.launch {
        val objects = mutableListOf<HomeScreenItem>()
        repository.fetchHomeGameWithList(gameIds).collect {
            when (it) {
                is Resource.Loading -> onLoading()
                is Resource.Success -> {
                    objects.add(it.data)
                    if (objects.size == gameIds.size) {
                        onSuccess(objects)
                    }
                }
                else -> onFailure()
            }
        }
    }

    private fun onFailure() {

    }

    private fun onSuccess(data: List<HomeScreenItem>) {
        _games.value = data
    }

    private fun onLoading() {

    }
}