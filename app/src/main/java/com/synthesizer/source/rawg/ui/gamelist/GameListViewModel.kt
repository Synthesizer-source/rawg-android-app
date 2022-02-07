package com.synthesizer.source.rawg.ui.gamelist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.synthesizer.source.rawg.core.UIState
import com.synthesizer.source.rawg.domain.model.GameListItem
import com.synthesizer.source.rawg.domain.usecase.FetchGameListUseCase
import com.synthesizer.source.rawg.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel
class GameListViewModel @Inject constructor(
    private val fetchGameListUseCase: FetchGameListUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private var _uiState: MutableStateFlow<UIState<PagingData<GameListItem>>?> =
        MutableStateFlow(null)
    val uiState: StateFlow<UIState<PagingData<GameListItem>>?> = _uiState.asStateFlow()

    init {
        fetchGames()
    }

    fun fetchGames() = viewModelScope.launch {
        val search = savedStateHandle.get<String>("search").orEmpty()
        val ordering = savedStateHandle.get<String>("ordering").orEmpty()
        val dates = savedStateHandle.get<String>("dates").orEmpty()
        fetchGameListUseCase(
            search = search,
            ordering = ordering,
            dates = dates
        ).cachedIn(viewModelScope)
            .onStart { _uiState.emit(UIState.Loading) }
            .catch { _uiState.emit(UIState.Error(it)) }
            .collect { _uiState.emit(UIState.Success(it)) }
    }
}