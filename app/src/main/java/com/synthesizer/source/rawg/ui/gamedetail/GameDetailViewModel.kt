package com.synthesizer.source.rawg.ui.gamedetail

import androidx.annotation.ColorRes
import androidx.lifecycle.*
import com.synthesizer.source.rawg.R
import com.synthesizer.source.rawg.data.Resource
import com.synthesizer.source.rawg.data.domain.GameDetail
import com.synthesizer.source.rawg.data.domain.GameImage
import com.synthesizer.source.rawg.data.usecase.FetchGameDetailUseCase
import com.synthesizer.source.rawg.data.usecase.FetchGameScreenshotsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GameDetailViewModel @AssistedInject constructor(
    private val fetchGameDetailUseCase: FetchGameDetailUseCase,
    private val fetchGameScreenshotsUseCase: FetchGameScreenshotsUseCase,
    @Assisted private val gameId: Int
) : ViewModel() {

    private var _gameDetail = MutableLiveData<GameDetail>()
    val gameDetail: LiveData<GameDetail> = _gameDetail

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _screenshots = MutableLiveData<List<GameImage>>()
    val screenshots: LiveData<List<GameImage>> = _screenshots

    private var _screenshotsVisibility = MutableLiveData<Boolean>()
    val screenshotsVisibility: LiveData<Boolean> = _screenshotsVisibility

    @ColorRes
    private var _metascoreColor: Int? = null
    val metascoreColor get() = _metascoreColor!!

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(gameId: Int): GameDetailViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            gameId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(gameId) as T
            }
        }
    }

    init {
        fetchGameDetail()
        fetchGameScreenshots()
    }

    private fun fetchGameDetail() = viewModelScope.launch {
        fetchGameDetailUseCase(gameId).collect {
            when (it) {
                is Resource.Loading -> onLoading()
                is Resource.Success -> onSuccess(it.data)
                else -> onFailure()
            }
        }
    }

    private fun fetchGameScreenshots() = viewModelScope.launch {
        fetchGameScreenshotsUseCase(gameId).collect {
            when (it) {
                is Resource.Loading -> onScreenshotLoading()
                is Resource.Success -> onScreenshotSuccess(it.data)
                else -> onFailure()
            }
        }
    }

    private fun onScreenshotSuccess(data: List<GameImage>) {
        if (!data.isNullOrEmpty() && data.isNotEmpty()) {
            _screenshots.value = data
            _screenshotsVisibility.value = true
        }
    }

    private fun onScreenshotLoading() {
        _screenshotsVisibility.value = false
    }

    private fun onSuccess(data: GameDetail) {
        _metascoreColor = when (data.metascore) {
            in 70..100 -> R.color.green_dark
            in 51..69 -> R.color.yellow_dark
            else -> R.color.red_dark
        }
        _gameDetail.value = data
        _isLoading.value = false
    }

    private fun onFailure() {

    }

    private fun onLoading() {
        _isLoading.value = true
    }
}