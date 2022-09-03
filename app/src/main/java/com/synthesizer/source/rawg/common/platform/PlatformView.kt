package com.synthesizer.source.rawg.common.platform

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat

class PlatformView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    init {
        orientation = HORIZONTAL
        showDividers = SHOW_DIVIDER_MIDDLE
    }

    fun initialize(platformUIModel: PlatformUIModel, config: PlatformConfig) {
        removeAllViews()
        dividerDrawable = ContextCompat.getDrawable(context, config.divider)

        val viewState = PlatformViewState(platformUIModel)

        viewState.getPlatformIcons().forEach { icon ->
            addView(ImageView(context).apply {
                val size = resources.getDimensionPixelSize(config.size)
                layoutParams = LayoutParams(size, size)
                setImageResource(icon)
            })
        }
    }
}