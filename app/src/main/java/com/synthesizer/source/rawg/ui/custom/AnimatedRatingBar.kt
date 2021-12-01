package com.synthesizer.source.rawg.ui.custom

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet

class AnimatedRatingBar(context: Context, attributeSet: AttributeSet?) :
    androidx.appcompat.widget.AppCompatRatingBar(context, attributeSet) {

    private var _animator: ValueAnimator? = null

    override fun onDetachedFromWindow() {
        _animator?.apply {
            pause()
            removeAllUpdateListeners()
        }
        _animator = null
        super.onDetachedFromWindow()
    }

    fun startAnimation(rating: Float, duration: Long = 500L) {
        _animator = ValueAnimator.ofFloat(0.00f, rating)
        _animator?.apply {
            setDuration(duration)
            addUpdateListener {
                setRating(it.animatedValue as Float)
            }
            start()
        }
    }
}