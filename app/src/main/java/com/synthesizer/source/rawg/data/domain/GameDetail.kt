package com.synthesizer.source.rawg.data.domain

data class GameDetail(
    val id: Int,
    val name: String,
    val backgroundImage: String,
    val releaseDate: String,
    val publisher: String,
    val rating: Double,
    val metascore: Int,
    val genres: List<String>,
    val description: String,
    val platforms: List<String>
)
