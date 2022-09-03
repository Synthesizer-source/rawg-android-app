package com.synthesizer.source.rawg.ui.gamedetail.component.summary

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.StyleRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.synthesizer.source.rawg.common.platform.PlatformConfig
import com.synthesizer.source.rawg.common.platform.PlatformUIModel
import com.synthesizer.source.rawg.databinding.LayoutGameDetailSummaryViewBinding
import com.synthesizer.source.rawg.utils.load
import com.synthesizer.source.rawg.utils.setMetascore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SummaryView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr, defStyleRes) {

    private val binding = LayoutGameDetailSummaryViewBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    fun initialize(summaryUIModel: SummaryUIModel) {
        binding.apply {
            val viewState = SummaryViewState(summaryUIModel)

            name.text = summaryUIModel.gameName
            publisherName.text = summaryUIModel.publisherName
            rating.startAnimation(summaryUIModel.rating)
            releaseDate.text = summaryUIModel.releaseDate


            platforms.initialize(
                platformUIModel = PlatformUIModel(summaryUIModel.platforms),
                config = PlatformConfig.Medium
            )

            metascore.setMetascore(
                summaryUIModel.metascore,
                viewState.getMetascoreColor()
            )

            genreChipGroup.load(summaryUIModel.genres)
        }
    }
}