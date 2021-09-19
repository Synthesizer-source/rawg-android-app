package com.synthesizer.source.rawg.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.synthesizer.source.rawg.data.Resource
import com.synthesizer.source.rawg.domain.model.GameImage
import com.synthesizer.source.rawg.domain.usecase.FetchGamesBackgroundImagesUseCase
import com.synthesizer.source.rawg.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val fetchGamesBackgroundImagesUseCase: FetchGamesBackgroundImagesUseCase) :
    BaseViewModel() {

    private var _games = MutableLiveData<List<GameImage>>()
    val games: LiveData<List<GameImage>> = _games

    private var _searchViewState = MutableLiveData(false) // 0 : Off , 1 : On
    val searchViewState: LiveData<Boolean> = _searchViewState

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
        fetchGamesBackgroundImagesUseCase(gameIds).collect {
            when (it) {
                is Resource.Loading -> onLoading()
                is Resource.Success -> onSuccess(it.data)
                is Resource.Failure.Error -> error(
                    errorCode = it.errorCode,
                    errorBody = it.errorBody
                )
                is Resource.Failure.Exception -> exception(it.throwable)
            }
        }
    }

    private fun onLoading() {}

    private fun onSuccess(data: GameImage) {
        if (_games.value.isNullOrEmpty()) _games.value = listOf(data)
        else {
            val gameList = _games.value!!
                .toMutableList()
            gameList.add(data)
            _games.value = gameList.distinctBy { it.id }
        }
    }

    fun setState(state: Boolean) {
        _searchViewState.value = state
    }
}