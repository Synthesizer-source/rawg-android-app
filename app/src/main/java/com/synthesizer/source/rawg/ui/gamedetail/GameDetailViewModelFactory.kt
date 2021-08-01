package com.synthesizer.source.rawg.ui.gamedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.synthesizer.source.rawg.repository.GameDetailRepository

class GameDetailViewModelFactory(
    private val gameId: Int,
    private val repository: GameDetailRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GameDetailViewModel(gameId, repository) as T
    }
}