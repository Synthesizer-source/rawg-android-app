package com.synthesizer.source.rawg.ui.gamedetail

import androidx.annotation.ColorRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.synthesizer.source.rawg.R
import com.synthesizer.source.rawg.data.Resource
import com.synthesizer.source.rawg.domain.model.GameDetail
import com.synthesizer.source.rawg.domain.model.GameImage
import com.synthesizer.source.rawg.domain.usecase.FetchGameDetailUseCase
import com.synthesizer.source.rawg.domain.usecase.FetchGameScreenshotsUseCase
import com.synthesizer.source.rawg.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class GameDetailViewModel @Inject constructor(
    private val fetchGameDetailUseCase: FetchGameDetailUseCase,
    private val fetchGameScreenshotsUseCase: FetchGameScreenshotsUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val gameId = savedStateHandle.get<Int>("gameId") ?: 1

    private var _detail = MutableStateFlow<GameDetail?>(null)
    val detail: StateFlow<GameDetail?>
        get() = _detail.asStateFlow()

    private var _screenshots = MutableStateFlow<List<GameImage>?>(null)
    val screenshots: StateFlow<List<GameImage>?>
        get() = _screenshots.asStateFlow()

    @ColorRes
    private var _metascoreColor: Int? = null
    val metascoreColor get() = _metascoreColor!!

    private var loadDataJob: Job? = null

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
                loading(false)
            }
        }
    }

    private suspend fun fetchGameDetail() {
        fetchGameDetailUseCase(gameId).collect {
            when (it) {
                is Resource.Loading -> loading()
                is Resource.Success -> onSuccess(it.data)
                is Resource.Error -> onError(it.throwable)
            }
        }
    }

    private suspend fun fetchGameScreenshots() {
        fetchGameScreenshotsUseCase(gameId).collect {
            when (it) {
                is Resource.Loading -> loading()
                is Resource.Success -> onScreenshotSuccess(it.data)
                is Resource.Error -> onError(it.throwable)
            }
        }
    }

    private fun onScreenshotSuccess(data: List<GameImage>) {
        if (data.isNullOrEmpty()) return
        viewModelScope.launch { _screenshots.emit(data) }
    }

    private fun onSuccess(data: GameDetail) {
        _metascoreColor = getMetascoreColor(data.metascore)
        viewModelScope.launch {
            _detail.emit(data)
        }
    }

    private fun onError(throwable: Throwable) {
        error(throwable) {
            loadDataJob?.cancel()
            loadDetailData()
        }
    }

    private fun getMetascoreColor(score: Int): Int {
        return when (score) {
            in 70..100 -> R.color.green_dark
            in 51..69 -> R.color.yellow_dark
            else -> R.color.red_dark
        }
    }

    fun getPlatformIcons(): IntArray {
        val icons = mutableListOf<Int>()
        detail.value?.let {
            it.platforms.forEach { platform ->
                when (platform) {
                    "pc" -> icons.add(R.drawable.ic_windows)
                    "playstation" -> icons.add(R.drawable.ic_playstation)
                    "xbox" -> icons.add(R.drawable.ic_xbox)
                    "nintendo" -> icons.add(R.drawable.ic_nintendo)
                }
            }
        }
        return icons.distinctBy { it }.toIntArray()
    }

    override fun onCleared() {
        super.onCleared()
        loadDataJob = null
    }
}