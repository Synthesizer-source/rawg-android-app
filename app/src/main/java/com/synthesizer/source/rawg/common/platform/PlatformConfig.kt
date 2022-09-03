package com.synthesizer.source.rawg.common.platform

import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import com.synthesizer.source.rawg.R

enum class PlatformConfig(@DimenRes val size: Int, @DrawableRes val divider: Int) {
    Small(
        size = R.dimen._12sdp,
        divider = R.drawable.drawable_small_platform_divider
    ),
    Medium(
        size = R.dimen._16sdp,
        divider = R.drawable.drawable_medium_platform_divider
    ),
}