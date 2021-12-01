package com.synthesizer.source.rawg.utils

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children

fun ViewGroup.hideChildren(vararg ignoredViews: View) {
    children.filter { c -> c !in ignoredViews }.forEach {
        it.visibility = View.INVISIBLE
    }
}

fun ViewGroup.showChildren(vararg ignoredViews: View) {
    children.filter { c -> c !in ignoredViews }.forEach {
        it.visibility = View.VISIBLE
    }
}