package com.synthesizer.source.rawg.ui.gamedetail.component.summary

import com.synthesizer.source.rawg.R

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

    companion object {
        const val PC = "pc"
        const val PLAYSTATION = "playstation"
        const val XBOX = "xbox"
        const val NINTENDO = "nintendo"
    }
}