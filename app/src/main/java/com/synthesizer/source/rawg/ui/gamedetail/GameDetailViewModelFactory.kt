package com.synthesizer.source.rawg.ui.gamedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GameDetailViewModelFactory(private val gameId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GameDetailViewModel(gameId) as T
    }
}