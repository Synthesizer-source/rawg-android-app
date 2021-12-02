package com.synthesizer.source.rawg.ui.gamelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.synthesizer.source.rawg.domain.model.GameListItem
import com.synthesizer.source.rawg.domain.usecase.FetchGameListUseCase
import com.synthesizer.source.rawg.ui.BaseViewModel
import com.synthesizer.source.rawg.utils.Event
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GameListViewModel @AssistedInject constructor(
    private val fetchGameListUseCase: FetchGameListUseCase,
    @Assisted("search") private val search: String,
    @Assisted("ordering") private val ordering: String,
    @Assisted("dates") private val dates: String
) : BaseViewModel() {

    private var _games = MutableLiveData<PagingData<GameListItem>>()
    val games: LiveData<PagingData<GameListItem>> = _games

    private var _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading

    init {
        setRetryRequest {
            fetchGames()
        }
        fetchGames()
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(
            @Assisted("search") search: String,
            @Assisted("ordering") ordering: String,
            @Assisted("dates") dates: String
        ): GameListViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            search: String,
            ordering: String,
            dates: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(search, ordering, dates) as T
            }
        }
    }

    private fun fetchGames() = viewModelScope.launch {
        fetchGameListUseCase(
            search = search,
            ordering = ordering,
            dates = dates
        ).cachedIn(this).collectLatest {
            _games.value = it
        }
    }

    fun loadStates(loadStates: CombinedLoadStates) {
        when {
            loadStates.refresh is LoadState.Error -> {
                exception((loadStates.refresh as LoadState.Error).error)
            }
            loadStates.append is LoadState.Error -> {
                exception((loadStates.append as LoadState.Error).error)
            }
            loadStates.prepend is LoadState.Error -> {
                exception((loadStates.prepend as LoadState.Error).error)
            }
        }
    }
}