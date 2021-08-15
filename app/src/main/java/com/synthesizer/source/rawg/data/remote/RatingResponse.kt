package com.synthesizer.source.rawg.data.remote

data class RatingResponse(
    val count: Int,
    val id: Int,
    val percent: Double,
    val title: String
)