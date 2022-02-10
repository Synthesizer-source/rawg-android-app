package com.synthesizer.source.rawg.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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

fun ImageView.loadImageWithCallback(
    url: String?,
    error: (() -> Unit)? = null,
    success: (() -> Unit)? = null,
) {
    Glide.get(context).setMemoryCategory(MemoryCategory.LOW)
    Glide
        .with(context)
        .load(url)
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .override(512)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                error?.invoke()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                success?.invoke()
                return false
            }

        })
        .centerInside()
        .into(this)
}