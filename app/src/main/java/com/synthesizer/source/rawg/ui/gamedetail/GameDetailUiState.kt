package com.synthesizer.source.rawg.ui.gamedetail

import androidx.annotation.ColorRes
import com.synthesizer.source.rawg.R
import com.synthesizer.source.rawg.domain.model.GameDetail

data class GameDetailUiState(val detail: GameDetail) {

    companion object {
        const val PC = "pc"
        const val PLAYSTATION = "playstation"
        const val XBOX = "xbox"
        const val NINTENDO = "nintendo"
        const val LOWER_GREEN = 70
        const val MAX_GREEN = 100
        const val LOWER_YELLOW = 51
        const val MAX_YELLOW = 69
    }

    @ColorRes
    fun getMetascoreColor(): Int {
        return when (detail.info.metascore) {
            in LOWER_GREEN..MAX_GREEN -> R.color.green_dark
            in LOWER_YELLOW..MAX_YELLOW -> R.color.yellow_dark
            else -> R.color.red_dark
        }
    }

    fun getPlatformIcons(): IntArray {
        val icons = mutableListOf<Int>()
        detail.info.platforms.forEach { platform ->
            when (platform) {
                PC -> icons.add(R.drawable.ic_windows)
                PLAYSTATION -> icons.add(R.drawable.ic_playstation)
                XBOX -> icons.add(R.drawable.ic_xbox)
                NINTENDO -> icons.add(R.drawable.ic_nintendo)
            }
        }
        return icons.toIntArray()
    }
}