package com.synthesizer.source.rawg.ui.gamelist.item

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.annotation.AttrRes
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.synthesizer.source.rawg.R
import com.synthesizer.source.rawg.common.platform.PlatformConfig
import com.synthesizer.source.rawg.databinding.LayoutGameListItemViewBinding
import com.synthesizer.source.rawg.domain.model.GameListItem
import com.synthesizer.source.rawg.utils.loadImage

class GameListItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = R.attr.attrGameListItemStyle
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val binding = LayoutGameListItemViewBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    fun initialize(gameListItem: GameListItem) {
        binding.apply {
            name.text = gameListItem.name
            background.loadImage(gameListItem.imageUrl)

            platforms.initialize(
                platformUIModel = gameListItem.platformUIModel,
                config = PlatformConfig.Small
            )
        }
    }

    fun clearImage(){
        Glide.with(this).clear(binding.background)
    }
}