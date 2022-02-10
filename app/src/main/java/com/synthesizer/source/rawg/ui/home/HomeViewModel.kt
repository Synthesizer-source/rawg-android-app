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
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchGamesBackgroundImagesUseCase: FetchGamesBackgroundImagesUseCase
) : BaseViewModel() {

    private val _images: MutableSharedFlow<List<GameImage>> = MutableSharedFlow()
    val images: SharedFlow<List<GameImage>> = _images.asSharedFlow()

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
        fetchGameImages()
    }

    private fun fetchGameImages() {
        viewModelScope.launch {
            fetchGamesBackgroundImagesUseCase(gameIds).collect {
                when (it) {
                    is Resource.Error -> error(it.throwable) {
                        cancel()
                        fetchGameImages()
                    }
                    is Resource.Loading -> _images.emit(it.data.orEmpty())
                    is Resource.Success -> _images.emit(it.data)
                }
            }
        }
    }
}