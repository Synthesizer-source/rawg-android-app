package com.synthesizer.source.rawg.common.platform

import com.synthesizer.source.rawg.R

class PlatformViewState(private val platformUIModel: PlatformUIModel) {

    fun getPlatformIcons(): List<Int> {
        val icons = mutableListOf<Int>()
        platformUIModel.platforms.forEach { platform ->
            when (platform) {
                PC -> icons.add(R.drawable.ic_windows)
                PLAYSTATION -> icons.add(R.drawable.ic_playstation)
                XBOX -> icons.add(R.drawable.ic_xbox)
                NINTENDO -> icons.add(R.drawable.ic_nintendo)
            }
        }
        return icons
    }

    companion object {
        const val PC = "pc"
        const val PLAYSTATION = "playstation"
        const val XBOX = "xbox"
        const val NINTENDO = "nintendo"
    }
}