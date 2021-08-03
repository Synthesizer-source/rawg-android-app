package com.synthesizer.source.rawg.ui

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import androidx.core.animation.doOnEnd
import androidx.core.view.get

class ExpandableLayout(context: Context, attributeSet: AttributeSet?) :
    LinearLayout(context, attributeSet) {

    companion object {
        const val EXPAND = 0x00000001
        const val COLLAPSE = 0x00000000
    }

    private var _isContentInitialized = false
    private var _currState = COLLAPSE
    val currState get() = _currState


    private var _header: View? = null
    private var _body: View? = null
    private val body get() = _body!!

    var animationDuration: Long = 1000L
    private var expandedBodyHeight = 0
    private var currBodyHeight = 0
    var minBodyHeight: Int = 0

    private var _isClickable = true

    var onExpandAnimationFinishedCallback: (Animator) -> Unit = { }
    var onCollapseAnimationFinishedCallback: (Animator) -> Unit = { }
    var onHeaderClickListener: (View) -> Unit = { }

    fun collapse() {
        _currState = COLLAPSE
        collapseAnimation(expandedBodyHeight, minBodyHeight)
    }

    fun expand() {
        _currState = EXPAND
        expandAnimation(minBodyHeight, expandedBodyHeight)
    }

    private fun collapseAnimation(start: Int, end: Int) {
        val anim = ValueAnimator.ofInt(start, end)
        anim.addUpdateListener {
            setBodyHeight(it.animatedValue as Int)
        }

        anim.doOnEnd {
            _isClickable = true
            onCollapseAnimationFinishedCallback(it)
        }
        anim.duration = animationDuration
        anim.start()
    }

    private fun expandAnimation(start: Int, end: Int) {
        val anim = ValueAnimator.ofInt(start, end)
        anim.addUpdateListener {
            setBodyHeight(it.animatedValue as Int)
        }

        anim.doOnEnd {
            _isClickable = true
            onExpandAnimationFinishedCallback(it)
        }

        anim.duration = animationDuration
        anim.start()
    }

    private fun setBodyHeight(height: Int) {
        val layoutParams = body.layoutParams
        layoutParams?.height = height
        body.layoutParams = layoutParams
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount < 2) return
        _header = get(0)
        _header!!.setOnClickListener {
            if (_isClickable) {
                _isClickable = false
                onHeaderClickListener(it)
            }
        }
        _body = get(1)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        body.measure(widthMeasureSpec, MeasureSpec.UNSPECIFIED)
        currBodyHeight = body.measuredHeight
        if (expandedBodyHeight < currBodyHeight) expandedBodyHeight = currBodyHeight
    }

    fun initialize(initialState: Int) {
        if (!_isContentInitialized) {
            when (initialState) {
                COLLAPSE -> {
                    _currState = COLLAPSE
                    body.viewTreeObserver.addOnGlobalLayoutListener(object :
                        ViewTreeObserver.OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            if (body.viewTreeObserver.isAlive) {
                                setBodyHeight(0)
                                body.viewTreeObserver.removeOnGlobalLayoutListener(this)

                            }
                        }

                    })
                }
                EXPAND -> {
                    _currState = EXPAND
                    body.viewTreeObserver.addOnGlobalLayoutListener(object :
                        ViewTreeObserver.OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            if (body.viewTreeObserver.isAlive) {
                                setBodyHeight(expandedBodyHeight)
                                body.viewTreeObserver.removeOnGlobalLayoutListener(this)

                            }
                        }

                    })
                }
                else -> throw IllegalArgumentException("undefined state parameter!")
            }
            _isContentInitialized = true
        }
    }
}