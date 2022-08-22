package com.synthesizer.source.rawg.ui.gamedetail.component.summary

import androidx.annotation.ColorRes
import com.synthesizer.source.rawg.R
import com.synthesizer.source.rawg.ui.gamedetail.GameDetailUiState

class SummaryViewState(private val summaryUIModel: SummaryUIModel) {

    fun getPlatformIcons(): IntArray {
        val icons = mutableListOf<Int>()
        summaryUIModel.platforms.forEach { platform ->
            when (platform) {
                PC -> icons.add(R.drawable.ic_windows)
                PLAYSTATION -> icons.add(R.drawable.ic_playstation)
                XBOX -> icons.add(R.drawable.ic_xbox)
                NINTENDO -> icons.add(R.drawable.ic_nintendo)
            }
        }
        return icons.toIntArray()
    }

    @ColorRes
    fun getMetascoreColor(): Int {
        return when (summaryUIModel.metascore) {
            in LOWER_GREEN..MAX_GREEN -> R.color.green_dark
            in LOWER_YELLOW..MAX_YELLOW -> R.color.yellow_dark
            else -> R.color.red_dark
        }
    }

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
}