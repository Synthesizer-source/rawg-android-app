package com.synthesizer.source.rawg.data.domain

data class GameDetailDomain(
    val id: Int,
    val name: String,
    val backgroundImage: String,
    val releaseDate: String,
    val publisher: String,
    val rating: Double,
    val description: String,
    val platforms: List<String>
)
