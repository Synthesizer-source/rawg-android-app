package com.synthesizer.source.rawg.ui.custom

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import com.synthesizer.source.rawg.R

class ShowMoreLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(context, attributeSet, defStyleAttr) {

    private var _textView: TextView? = null
    private val textView get() = _textView!!
    private var _button: Button? = null
    private val button get() = _button!!
    private var _currState: Int = 0 // 0 : hide , 1 : show
    private var _collapseLines = 0

    private var _maxHeight = 0
    private var _minHeight = 0
    private var _currHeight = 0

    var animationDuration: Long = 1000L

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_show_hide, this)
        attributeSet?.let {
            val typedArray: TypedArray =
                context.obtainStyledAttributes(it, R.styleable.ShowMoreLayout, 0, 0)
            typedArray.getString(R.styleable.ShowMoreLayout_initialState)?.let { state ->
                _currState = state.toInt()
            }
            typedArray.getString(R.styleable.ShowMoreLayout_collapseLines)?.let { state ->
                _collapseLines = state.toInt()
                if (_collapseLines < 0) throw IllegalArgumentException("collapseLines can not be negative!!")
            }
            typedArray.recycle()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        _textView = (get(0) as LinearLayout)[0] as TextView
        _button = (get(0) as LinearLayout)[1] as Button

        if (_currState == 0) {
            textView.maxLines = _collapseLines
        }

        button.setOnClickListener {
            if (_currState == 0) {
//                textView.maxLines = textView.lineCount
                expandAnimation(_minHeight, _maxHeight)
                _currState = 1
            } else {
//                textView.maxLines = _collapseLines
                collapseAnimation(_maxHeight, _minHeight)
                _currState = 0
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        _currHeight = textView.measuredHeight
        if (_maxHeight == 0) {
            _maxHeight = textView.layout.height + textView.paddingTop + textView.paddingBottom
        }

        if (_currHeight > _maxHeight) _maxHeight = _currHeight

        if (_minHeight == 0) {
            _minHeight = if (_currState == 0) {
                textView.measuredHeight
            } else {
                // not working
                ((_collapseLines * (textView.paint.fontMetrics.bottom - textView.paint.fontMetrics.top)) - textView.paint.fontMetrics.top - textView.paint.fontMetrics.bottom).toInt()
            }
        }

        Log.d(
            "synthesizer-source",
            "onFinishInflate: ${_maxHeight} " +
                    " ${textView.measuredHeight} " +
                    " ${_minHeight}"
        )
    }

    private fun setBodyHeight(height: Int) {
        val layoutParams = textView.layoutParams
        layoutParams?.height = height
        textView.layoutParams = layoutParams
    }

    private fun expandAnimation(start: Int, end: Int) {
        var diffHeight = end - start
        var diffLines = textView.lineCount - _collapseLines
        var currShowedLines = _collapseLines
        val anim = ValueAnimator.ofInt(start, end)
        anim.addUpdateListener {
            val value = it.animatedValue as Int
            if (diffLines == 0) textView.maxLines = textView.lineCount
            else if (value >= (diffHeight / diffLines)) {
                currShowedLines++
                diffHeight -= value
                diffLines--
                textView.maxLines = currShowedLines
            }
            setBodyHeight(value)
        }

        anim.duration = animationDuration
        anim.start()
    }

    private fun collapseAnimation(start: Int, end: Int) {
        var diffHeight = start - end
        var diffLines = textView.lineCount - _collapseLines
        var currShowedLines = textView.lineCount
        val anim = ValueAnimator.ofInt(start, end)
        anim.addUpdateListener {
            val value = it.animatedValue as Int
            if (diffLines == 0) textView.maxLines = _collapseLines
            if (value == diffHeight) {
                currShowedLines--
                diffHeight -= value
                diffLines--
                textView.maxLines = currShowedLines
            }
            setBodyHeight(value)
        }

        anim.duration = animationDuration
        anim.start()
    }
}