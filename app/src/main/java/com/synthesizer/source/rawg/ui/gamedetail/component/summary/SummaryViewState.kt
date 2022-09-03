package com.synthesizer.source.rawg.ui.gamedetail.component.summary

import androidx.annotation.ColorRes
import com.synthesizer.source.rawg.R

class SummaryViewState(private val summaryUIModel: SummaryUIModel) {

    @ColorRes
    fun getMetascoreColor(): Int {
        return when (summaryUIModel.metascore) {
            in LOWER_GREEN..MAX_GREEN -> R.color.green_dark
            in LOWER_YELLOW..MAX_YELLOW -> R.color.yellow_dark
            else -> R.color.red_dark
        }
    }

    companion object {
        const val LOWER_GREEN = 70
        const val MAX_GREEN = 100
        const val LOWER_YELLOW = 51
        const val MAX_YELLOW = 69
    }
}