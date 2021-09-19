package com.synthesizer.source.rawg.ui.gamelist

import androidx.lifecycle.*
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.synthesizer.source.rawg.data.Resource
import com.synthesizer.source.rawg.domain.model.GameListItem
import com.synthesizer.source.rawg.domain.usecase.FetchGameListUseCase
import com.synthesizer.source.rawg.ui.BaseViewModel
import com.synthesizer.source.rawg.utils.Event
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
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
        ).collectLatest {
            when (it) {
                is Resource.Failure.Error -> error(it.errorCode, it.errorBody)
                is Resource.Failure.Exception -> {
                    exception(it.throwable)
                }
                is Resource.Loading -> onLoading()
                is Resource.Success -> onSuccess(it)
            }
        }
    }

    private suspend fun onSuccess(resource: Resource.Success<Flow<PagingData<GameListItem>>>) {
        resource.data.cachedIn(viewModelScope).collectLatest { data ->
            _games.value = data
            delay(1500)
            _isLoading.value = Event(false)
        }
    }

    private fun onLoading() {
        _isLoading.value = Event(true)
    }

    fun loadStates(loadStates: CombinedLoadStates) {

        if (loadStates.refresh is LoadState.Error) {
            exception((loadStates.refresh as LoadState.Error).error)
        } else if (loadStates.append is LoadState.Error) {
            exception((loadStates.append as LoadState.Error).error)
        }
    }
}