package com.synthesizer.source.rawg.data.domain

data class GameListItem(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val platforms: List<String>
)