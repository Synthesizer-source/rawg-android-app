package com.synthesizer.source.rawg.ui.gamelist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.synthesizer.source.rawg.core.domain.model.SearchParams
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

    private var _games: MutableStateFlow<PagingData<GameListItem>?> =
        MutableStateFlow(null)
    val games: StateFlow<PagingData<GameListItem>?> = _games.asStateFlow()

    init {
        fetchGames()
    }

    fun fetchGames() {
        viewModelScope.launch {
            val searchParams = savedStateHandle.get<SearchParams>("searchParams")
            searchParams?.apply {
                fetchGameListUseCase(
                    search = search,
                    ordering = ordering,
                    dates = dates
                ).cachedIn(viewModelScope)
                    .onStart { loading() }
                    .catch { error(it) { fetchGames() } }
                    .collect { _games.emit(it) }
            }
        }
    }
}