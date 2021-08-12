package com.synthesizer.source.rawg.utils

import android.content.res.Resources
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String?) {
    Glide
        .with(context)
        .load(url)
        .override(Resources.getSystem().displayMetrics.widthPixels)
        .into(this)
}