package com.synthesizer.source.rawg.ui.home

import android.view.View
import androidx.annotation.Px
import androidx.viewpager2.widget.ViewPager2

class OffsetPageTransformer(
    @Px private val offsetPx: Int,
    @Px private val pageMarginPx: Int
) : ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        val offset = position * -(2 * offsetPx + pageMarginPx)

        if (position < -1) {
            page.translationX = -offset
        } else if (position <= 1) {
            val scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f))
            page.translationX = offset
            page.scaleY = scaleFactor
            page.alpha = scaleFactor
        } else {
            page.alpha = 0f
            page.translationX = offset
        }
    }
}