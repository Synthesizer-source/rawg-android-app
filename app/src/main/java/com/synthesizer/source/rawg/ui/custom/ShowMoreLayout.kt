package com.synthesizer.source.rawg.ui.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
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
    private var _maxLines = 0

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_show_hide, this)
        attributeSet?.let {
            val typedArray: TypedArray =
                context.obtainStyledAttributes(it, R.styleable.ShowMoreLayout, 0, 0)
            typedArray.getString(R.styleable.ShowMoreLayout_initialState)?.let { state ->
                _currState = state.toInt()
            }
            typedArray.recycle()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        _textView = (get(0) as LinearLayout)[0] as TextView
        _maxLines = textView.maxLines
        _button = (get(0) as LinearLayout)[1] as Button

        button.setOnClickListener {
            if (_currState == 0) {
                textView.maxLines = textView.lineCount
                _currState = 1
            } else {
                textView.maxLines = _maxLines
                _currState = 0
            }
        }
    }
}