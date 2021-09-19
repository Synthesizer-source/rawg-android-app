package com.synthesizer.source.rawg.ui.gamedetail

import androidx.annotation.ColorRes
import androidx.lifecycle.*
import com.synthesizer.source.rawg.R
import com.synthesizer.source.rawg.data.Resource
import com.synthesizer.source.rawg.domain.model.GameDetail
import com.synthesizer.source.rawg.domain.model.GameImage
import com.synthesizer.source.rawg.domain.usecase.FetchGameDetailUseCase
import com.synthesizer.source.rawg.domain.usecase.FetchGameScreenshotsUseCase
import com.synthesizer.source.rawg.ui.BaseViewModel
import com.synthesizer.source.rawg.utils.Event
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GameDetailViewModel @AssistedInject constructor(
    private val fetchGameDetailUseCase: FetchGameDetailUseCase,
    private val fetchGameScreenshotsUseCase: FetchGameScreenshotsUseCase,
    @Assisted private val gameId: Int
) : BaseViewModel() {

    private var _gameDetail = MutableLiveData<GameDetail>()
    val gameDetail: LiveData<GameDetail> = _gameDetail

    private var _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading

    private var _screenshots = MutableLiveData<List<GameImage>>()
    val screenshots: LiveData<List<GameImage>> = _screenshots

    private var _screenshotsVisibility = MutableLiveData<Event<Boolean>>()
    val screenshotsVisibility: LiveData<Event<Boolean>> = _screenshotsVisibility

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
        viewModelScope.launch {
            val deffered = listOf(
                async { fetchGameDetail() },
                async { fetchGameScreenshots() }
            )
            deffered.awaitAll()
        }
    }

    private fun fetchGameDetail() = viewModelScope.launch {
        fetchGameDetailUseCase(gameId).collect {
            when (it) {
                is Resource.Loading -> onLoading()
                is Resource.Success -> onSuccess(it.data)
                is Resource.Failure.Error -> error(it.errorCode, it.errorBody)
                is Resource.Failure.Exception -> exception(it.throwable)
            }
        }
    }

    private fun fetchGameScreenshots() = viewModelScope.launch {
        fetchGameScreenshotsUseCase(gameId).collect {
            when (it) {
                is Resource.Loading -> onScreenshotLoading()
                is Resource.Success -> onScreenshotSuccess(it.data)
                is Resource.Failure.Error -> error(it.errorCode, it.errorBody)
                is Resource.Failure.Exception -> super.exception(it.throwable)
            }
        }
    }

    private fun onScreenshotSuccess(data: List<GameImage>) {
        if (!data.isNullOrEmpty() && data.isNotEmpty()) {
            _screenshots.value = data
            _screenshotsVisibility.value = Event(true)
        }
    }

    private fun onScreenshotLoading() {
        _screenshotsVisibility.value = Event(false)
    }

    private fun onSuccess(data: GameDetail) {
        _metascoreColor = when (data.metascore) {
            in 70..100 -> R.color.green_dark
            in 51..69 -> R.color.yellow_dark
            else -> R.color.red_dark
        }
        _gameDetail.value = data
        _isLoading.value = Event(false)
    }

    private fun onLoading() {
        _isLoading.value = Event(true)
    }
}