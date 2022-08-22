package com.synthesizer.source.rawg.ui.gamedetail.component.screenshot

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.StyleRes
import com.synthesizer.source.rawg.databinding.LayoutGameDetailScreenshotViewBinding

class ScreenshotView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    val binding = LayoutGameDetailScreenshotViewBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    init {
        orientation = VERTICAL
    }

    fun initialize(screenshotUIModel: ScreenshotUIModel) {
        binding.screenshots.adapter = ScreenshotAdapter().apply {
            submitList(screenshotUIModel.screenshots)
        }
    }
}