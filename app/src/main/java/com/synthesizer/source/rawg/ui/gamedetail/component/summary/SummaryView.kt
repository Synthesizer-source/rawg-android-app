package com.synthesizer.source.rawg.ui.gamedetail.component.summary

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.StyleRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.synthesizer.source.rawg.databinding.LayoutProductDetailSummaryViewBinding
import com.synthesizer.source.rawg.utils.addPlatformIcons
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SummaryView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr, defStyleRes) {

    private val binding = LayoutProductDetailSummaryViewBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    fun initialize(summaryUIModel: SummaryUIModel) {
        binding.apply {
            name.text = summaryUIModel.gameName
            publisherName.text = summaryUIModel.publisherName
            rating.startAnimation(summaryUIModel.rating)
            releaseDate.text = summaryUIModel.releaseDate
            platforms.addPlatformIcons(icons = SummaryViewState(summaryUIModel).getPlatformIcons())
        }
    }
}