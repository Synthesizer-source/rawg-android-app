package com.synthesizer.source.rawg.ui.gamelist

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.synthesizer.source.rawg.data.Resource
import com.synthesizer.source.rawg.data.domain.GameListItem
import com.synthesizer.source.rawg.repository.HomeRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GameListViewModel @AssistedInject constructor(
    private val repository: HomeRepository,
    @Assisted("search") private val search: String,
    @Assisted("ordering") private val ordering: String,
    @Assisted("dates") private val dates: String
) : ViewModel() {

    private var _games = MutableLiveData<PagingData<GameListItem>>()
    val games: LiveData<PagingData<GameListItem>> = _games

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

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
        repository.fetchGames(
            search = search,
            ordering = ordering,
            dates = dates
        ).collect {
            when (it) {
                is Resource.Loading -> _isLoading.value = true
                is Resource.Success -> {
                    it.data.cachedIn(this).collectLatest { data ->
                        _games.value = data
                        delay(1500L)
                        _isLoading.value = false
                    }
                }
                is Resource.Failure -> {
                }
            }
        }
    }
}