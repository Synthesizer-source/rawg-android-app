package com.synthesizer.source.rawg.ui.gamedetail

import androidx.annotation.ColorRes
import androidx.lifecycle.*
import com.synthesizer.source.rawg.R
import com.synthesizer.source.rawg.data.Resource
import com.synthesizer.source.rawg.data.domain.GameDetailDomain
import com.synthesizer.source.rawg.repository.GameDetailRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GameDetailViewModel @AssistedInject constructor(
    private val repository: GameDetailRepository,
    @Assisted private val gameId: Int
) : ViewModel() {

    private var _gameDetail = MutableLiveData<GameDetailDomain>()
    val gameDetail: LiveData<GameDetailDomain> = _gameDetail

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

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
    }

    private fun fetchGameDetail() = viewModelScope.launch {
        repository.fetchGameDetail(gameId).collect {
            when (it) {
                is Resource.Loading -> onLoading()
                is Resource.Success -> onSuccess(it.data)
                else -> onFailure()
            }
        }
    }

    private fun onSuccess(data: GameDetailDomain) {
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