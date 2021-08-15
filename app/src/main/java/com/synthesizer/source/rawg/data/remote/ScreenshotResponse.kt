package com.synthesizer.source.rawg.data.remote

data class ScreenshotResponse(
    val height: Int,
    val id: Int,
    val image: String,
    val is_deleted: Boolean,
    val width: Int
)