package com.synthesizer.source.rawg.data.remote

data class StoreDetail(
    val domain: String,
    val games_count: Int,
    val id: Int,
    val image_background: String,
    val name: String,
    val slug: String
)