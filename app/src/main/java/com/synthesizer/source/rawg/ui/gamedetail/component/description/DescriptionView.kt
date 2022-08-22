package com.synthesizer.source.rawg.ui.gamedetail.component.description

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.StyleRes
import com.synthesizer.source.rawg.databinding.LayoutGameDetailDescriptionViewBinding

class DescriptionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding = LayoutGameDetailDescriptionViewBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    init {
        orientation = VERTICAL
    }

    fun initialize(descriptionUIModel: DescriptionUIModel) {
        binding.description.setBodyContent(descriptionUIModel.description)
    }
}