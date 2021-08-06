package com.synthesizer.source.rawg.ui.custom

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.animation.doOnEnd
import androidx.core.view.get
import com.synthesizer.source.rawg.R

class ShowMoreLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    LinearLayout(context, attributeSet, defStyleAttr) {

    private var _body: TextView? = null
    private val body get() = _body!!
    private var _button: Button? = null
    private val button get() = _button!!

    private var _currState: Int = 0 // 0 : hide , 1 : show
    val currentState get() = _currState
    private var _collapseLines = 0
    private var _isClickable = true

    private var _maxHeight = 0
    private var _minHeight = 0
    private var _currHeight = 0

    private var _typedArray: TypedArray? = null
    private val typedArray get() = _typedArray!!

    private var _animationDuration: Long = 1000L

    var onExpandAnimationFinishedCallback: () -> Unit = { }
    var onCollapseAnimationFinishedCallback: () -> Unit = { }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_show_hide, this)
        attributeSet?.let {
            _typedArray =
                context.obtainStyledAttributes(it, R.styleable.ShowMoreLayout, 0, 0)

            typedArray.getString(R.styleable.ShowMoreLayout_initialState)?.let { state ->
                _currState = state.toInt()
            }

            typedArray.getInt(R.styleable.ShowMoreLayout_animationDuration, 1000).let { duration ->
                _animationDuration = if (duration > 0) duration.toLong() else 0
            }
        }
    }

    private fun setUpBody() {
        typedArray.getString(R.styleable.ShowMoreLayout_collapseLines)!!.let {
            _collapseLines =
                if (body.lineCount < _collapseLines) body.lineCount else it.toInt()
            if (_collapseLines <= 0) throw IllegalArgumentException("collapseLines must be positive!!")
        }
        typedArray.getString(R.styleable.ShowMoreLayout_bodyContent)?.let { body.text = it }
        typedArray.getColorStateList(R.styleable.ShowMoreLayout_bodyTextColor)?.let {
            body.setTextColor(it)
        }
        typedArray.getDrawable(R.styleable.ShowMoreLayout_bodyDrawable)
            ?.let { body.background = it }
    }

    private fun setUpButton() {
        typedArray.getString(R.styleable.ShowMoreLayout_buttonText)?.let { button.text = it }
        typedArray.getColorStateList(R.styleable.ShowMoreLayout_buttonTextColor)?.let {
            button.setTextColor(it)
        }

        typedArray.getDrawable(R.styleable.ShowMoreLayout_buttonForeground)
            ?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    button.foreground = it
                }
            }
        typedArray.recycle()
    }

    fun setButtonText(text: String) {
        button.setText(text)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        _body = get(0) as TextView
        _button = get(1) as Button

        setUpBody()
        setUpButton()

        if (_currState == 0) {
            body.maxLines = _collapseLines
        }

        button.setOnClickListener {
            if (button.isClickable) {
                button.isClickable = false
                if (_currState == 0) {
                    expandAnimation(_minHeight, _maxHeight)
                    _currState = 1
                } else {
                    collapseAnimation(_maxHeight, _minHeight)
                    _currState = 0
                }
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        _currHeight = body.measuredHeight
        if (_maxHeight == 0) {
            _maxHeight = body.layout.height + body.paddingTop + body.paddingBottom
        }

        if (_currHeight > _maxHeight) _maxHeight = _currHeight

        if (_minHeight == 0) {
            _minHeight = if (_currState == 0) {
                body.measuredHeight
            } else {
                ((_collapseLines * (body.paint.fontMetrics.bottom - body.paint.fontMetrics.top)) + body.paddingTop + body.paddingBottom - body.paint.fontMetrics.bottom).toInt()
            }
        }
    }

    private fun setBodyHeight(height: Int) {
        val layoutParams = body.layoutParams
        layoutParams?.height = height
        body.layoutParams = layoutParams
    }

    private fun expandAnimation(start: Int, end: Int) {
        var diffHeight = end - start
        var diffLines = body.lineCount - _collapseLines
        var currShowedLines = _collapseLines
        val anim = ValueAnimator.ofInt(start, end)
        anim.addUpdateListener {
            val value = it.animatedValue as Int
            if (diffLines == 0) body.maxLines = body.lineCount
            else if (value >= (diffHeight / diffLines)) {
                currShowedLines++
                diffHeight -= value
                diffLines--
                body.maxLines = currShowedLines
            }
            setBodyHeight(value)
        }

        anim.doOnEnd {
            onExpandAnimationFinishedCallback()
            button.isClickable = true
        }

        anim.duration = _animationDuration
        anim.start()
    }

    private fun collapseAnimation(start: Int, end: Int) {
        var diffHeight = start - end
        var diffLines = body.lineCount - _collapseLines
        var currShowedLines = body.lineCount
        val anim = ValueAnimator.ofInt(start, end)
        anim.addUpdateListener {
            val value = it.animatedValue as Int
            if (diffLines == 0) body.maxLines = _collapseLines
            if (value == diffHeight) {
                currShowedLines--
                diffHeight -= value
                diffLines--
                body.maxLines = currShowedLines
            }
            setBodyHeight(value)
        }

        anim.doOnEnd {
            onCollapseAnimationFinishedCallback()
            button.isClickable = true
        }

        anim.duration = _animationDuration
        anim.start()
    }
}