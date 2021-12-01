package com.synthesizer.source.rawg.utils

import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

fun ChipGroup.load(list: List<String>) {
    list.forEach {
        addView(Chip(context).also { c -> c.text = it })
    }
}