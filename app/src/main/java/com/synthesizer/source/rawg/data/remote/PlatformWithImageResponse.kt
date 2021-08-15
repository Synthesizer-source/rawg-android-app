package com.synthesizer.source.rawg.data.remote

data class PlatformWithImageResponse(
    val games_count: Int,
    val id: Int,
    val image: Any,
    val image_background: String,
    val name: String,
    val slug: String,
    val year_end: Any,
    val year_start: Int
)