package com.synthesizer.source.rawg.ui.home

import androidx.lifecycle.viewModelScope
import com.synthesizer.source.rawg.data.Resource
import com.synthesizer.source.rawg.domain.model.GameImage
import com.synthesizer.source.rawg.domain.usecase.FetchGamesBackgroundImagesUseCase
import com.synthesizer.source.rawg.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchGamesBackgroundImagesUseCase: FetchGamesBackgroundImagesUseCase
) : BaseViewModel() {

    private val _gameImages: MutableSharedFlow<GameImage> = MutableSharedFlow()
    val gamesImages: SharedFlow<GameImage> = _gameImages.asSharedFlow()

    // 0 : Off , 1 : On
    private val _searchViewState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val searchViewState: StateFlow<Boolean> = _searchViewState.asStateFlow()

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

    private fun fetchGames() {
        viewModelScope.launch {
            fetchGamesBackgroundImagesUseCase(gameIds).collect {
                when (it) {
                    is Resource.Loading -> {}
                    is Resource.Success -> _gameImages.emit(it.data)
                    is Resource.Error -> error(it.throwable) {
                        cancel()
                        fetchGames()
                    }
                }
            }
        }
    }

    fun setState(state: Boolean) {
        viewModelScope.launch {
            _searchViewState.emit(state)
        }
    }

    fun switchSearchState() {
        viewModelScope.launch {
            _searchViewState.emit(!_searchViewState.value)
        }
    }
}