package com.synthesizer.source.rawg.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.synthesizer.source.rawg.R

fun ImageView.loadImage(url: String?) {

    Glide.get(context).setMemoryCategory(MemoryCategory.LOW)

    Glide
        .with(context)
        .load(url)
        .thumbnail(0.5f)
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .override(512)
        .dontAnimate()
        .placeholder(R.drawable.placeholder)
        .centerInside()
        .into(this)
}