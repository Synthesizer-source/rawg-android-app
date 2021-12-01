package com.synthesizer.source.rawg.utils

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton

fun MaterialButton.setMetascore(score: Int, @ColorRes colorRes: Int) {
    setTextColor(ContextCompat.getColor(context, colorRes))
    setStrokeColorResource(colorRes)
    text = score.toString()
}