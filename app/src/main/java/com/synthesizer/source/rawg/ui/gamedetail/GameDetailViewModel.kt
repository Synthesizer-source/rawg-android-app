package com.synthesizer.source.rawg.ui.gamedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.synthesizer.source.rawg.data.Resource
import com.synthesizer.source.rawg.domain.model.GameDetailWithScreenshots
import com.synthesizer.source.rawg.domain.usecase.FetchGameDetailWithScreenshotsUseCase
import com.synthesizer.source.rawg.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class GameDetailViewModel @Inject constructor(
    private val fetchGameDetailWithScreenshotsUseCase: FetchGameDetailWithScreenshotsUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val gameId = savedStateHandle.get<Int>("gameId") ?: 1

    private var _detail = MutableStateFlow<GameDetailWithScreenshots?>(null)
    val detail: StateFlow<GameDetailWithScreenshots?>
        get() = _detail.asStateFlow()

    private var job: Job? = null

    init {
        fetchGameDetailWithScreenshots()
    }

    private fun fetchGameDetailWithScreenshots() {
        job = viewModelScope.launch {
            fetchGameDetailWithScreenshotsUseCase(gameId).collect {
                when (it) {
                    is Resource.Error -> onError(it.throwable)
                    is Resource.Loading -> loading()
                    is Resource.Success -> onSuccessNewGameDetail(it.data)
                }
            }
        }
    }

    private suspend fun onSuccessNewGameDetail(newGameDetail: GameDetailWithScreenshots) {
        _detail.emit(newGameDetail)
        loading(false)
    }

    private fun onError(throwable: Throwable) {
        error(throwable) {
            job?.cancel()
            fetchGameDetailWithScreenshots()
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
        job = null
    }
}