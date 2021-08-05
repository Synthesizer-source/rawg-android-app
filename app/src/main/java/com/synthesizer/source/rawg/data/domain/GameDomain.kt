package com.synthesizer.source.rawg.data.domain

data class GameDomain(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val platforms: List<String>
)