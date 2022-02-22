package com.synthesizer.source.rawg.domain.model

data class GameDetailInfo(
    val id: Int,
    val name: String,
    val backgroundImage: String,
    val releaseDate: String,
    val publisher: String,
    val rating: Float,
    val metascore: Int,
    val genres: List<String>,
    val description: String,
    val platforms: List<String>
)