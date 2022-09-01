package com.synthesizer.source.rawg.ui.gamedetail.component.header

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.StyleRes
import com.synthesizer.source.rawg.databinding.LayoutGameDetailHeaderViewBinding
import com.synthesizer.source.rawg.utils.loadImage

class HeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding =
        LayoutGameDetailHeaderViewBinding.inflate(LayoutInflater.from(context), this)

    fun initialize(headerUIModel: HeaderUIModel) {
        binding.apply {
            image.loadImage(headerUIModel.image)
        }
    }
}