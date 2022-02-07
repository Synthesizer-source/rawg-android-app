package com.synthesizer.source.rawg.ui.gamedetail

import androidx.annotation.ColorRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.Job
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

    @ColorRes
    private var _metascoreColor: Int? = null
    val metascoreColor get() = _metascoreColor!!

    private var loadDataJob: Job? = null

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
        loadDetailData()
    }

    private fun loadDetailData() {
        loadDataJob = viewModelScope.launch {
            val deffered = listOf(
                async { fetchGameDetail() },
                async { fetchGameScreenshots() }
            )

            deffered.awaitAll().also {
                _isLoading.value = Event(false)
            }
        }
    }

    private suspend fun fetchGameDetail() {
        fetchGameDetailUseCase(gameId).collect {
            when (it) {
                is Resource.Loading -> onLoading()
                is Resource.Success -> onSuccess(it.data)
                is Resource.Error -> error(it.throwable) {
                    loadDataJob?.cancel()
                    loadDetailData()
                }
            }
        }
    }

    private suspend fun fetchGameScreenshots() {
        fetchGameScreenshotsUseCase(gameId).collect {
            when (it) {
                is Resource.Loading -> onLoading()
                is Resource.Success -> onScreenshotSuccess(it.data)
                is Resource.Error -> error(it.throwable) {
                    loadDataJob?.cancel()
                    loadDetailData()
                }
            }
        }
    }

    private fun onScreenshotSuccess(data: List<GameImage>) {
        if (!data.isNullOrEmpty()) {
            _screenshots.value = data
        }
    }

    private fun onSuccess(data: GameDetail) {
        _metascoreColor = when (data.metascore) {
            in 70..100 -> R.color.green_dark
            in 51..69 -> R.color.yellow_dark
            else -> R.color.red_dark
        }
        _gameDetail.value = data
    }

    private fun onLoading() {
        if (_isLoading.value == null) _isLoading.value = Event(true)
    }

    fun getPlatformIcons(): IntArray {
        val icons = mutableListOf<Int>()
        gameDetail.value?.platforms?.forEach {
            when (it) {
                "pc" -> icons.add(R.drawable.ic_windows)
                "playstation" -> icons.add(R.drawable.ic_playstation)
                "xbox" -> icons.add(R.drawable.ic_xbox)
                "nintendo" -> icons.add(R.drawable.ic_nintendo)
            }
        }
        return icons.distinctBy { it }.toIntArray()
    }

    override fun onCleared() {
        super.onCleared()
        loadDataJob = null
    }
}