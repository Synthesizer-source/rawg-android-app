package com.synthesizer.source.rawg.ui.gamedetail

import androidx.annotation.ColorRes
import com.synthesizer.source.rawg.R
import com.synthesizer.source.rawg.domain.model.GameDetail

const val PC = "pc"
const val PLAYSTATION = "playstation"
const val XBOX = "xbox"
const val NINTENDO = "nintendo"

class GameDetailViewState(private val detail: GameDetail) {
    @ColorRes
    fun getMetascoreColor(): Int {
        return when (detail.metascore) {
            in 70..100 -> R.color.green_dark
            in 51..69 -> R.color.yellow_dark
            else -> R.color.red_dark
        }
    }

    fun getPlatformIcons(): IntArray {
        val icons = mutableListOf<Int>()
        detail.platforms.forEach { platform ->
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